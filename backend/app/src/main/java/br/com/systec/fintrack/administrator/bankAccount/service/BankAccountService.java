package br.com.systec.fintrack.administrator.bankAccount.service;

import br.com.systec.fintrack.administrator.bankAccount.exceptions.BankAccountNotFoundException;
import br.com.systec.fintrack.administrator.bankAccount.filter.BankAccountFilterVO;
import br.com.systec.fintrack.administrator.bankAccount.jms.BankAccountJmsVO;
import br.com.systec.fintrack.administrator.bankAccount.model.BankAccount;
import br.com.systec.fintrack.administrator.bankAccount.repository.BankAccountRepository;
import br.com.systec.fintrack.administrator.bankAccount.repository.BankAccountRepositoryJPA;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.financial.accountReceivable.exceptions.AccountReceivableException;
import br.com.systec.fintrack.financial.transaction.enums.TransactionType;
import br.com.systec.fintrack.financial.transaction.model.Transaction;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankAccountService {
    private static final Logger log = LoggerFactory.getLogger(BankAccountService.class);
    @Autowired
    private BankAccountRepository repository;
    @Autowired
    private BankAccountRepositoryJPA repositoryJPA;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccount save(BankAccount bankAccount) {
        if (bankAccount.getTenantId() == null) {
            bankAccount.setTenantId(TenantContext.getTenant());
        }

        BankAccount bankAccountSaved = repository.save(bankAccount);

        if (bankAccount.getInitialValue() > 0.0) {
            saveAmountInitialAccount(bankAccountSaved);
        }

        return bankAccountSaved;
    }

    private void saveAmountInitialAccount(BankAccount bankAccount) {
        try {
            BankAccountJmsVO bankAccountJmsVO = new BankAccountJmsVO(TenantContext.getTenant(), bankAccount.getId(), bankAccount.getInitialValue());

            rabbitTemplate.convertAndSend(RabbitMQConstants.FINANCIAL_EXCHANGE, RabbitMQConstants.ROUTING_KEY_NEW_BANK_ACCOUNT, bankAccountJmsVO);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar enviar a mensagem para salvar o valor inicial", e);
            throw new AccountReceivableException(I18nTranslate.toLocale("error.save.account.opening.balance"));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccount update(BankAccount bankAccount) {
        if (bankAccount.getTenantId() == null) {
            bankAccount.setTenantId(TenantContext.getTenant());
        }

        return repository.update(bankAccount);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BankAccount findById(Long id) {
        return findAccountById(id);
    }

    private BankAccount findAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(BankAccountNotFoundException::new);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<BankAccount> findByFilter(BankAccountFilterVO filterVO) {
        Page<BankAccount> listPageBankAccount = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());

        return listPageBankAccount;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findCurrentAccountBalance() {
        return repository.findCurrentAccountBalance();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBankAccountBalance(Double amount, Long bankAccountId, TransactionType transactionType) throws BaseException {
        try {
            BankAccount bankAccount = findById(bankAccountId);
            double balanceOld = bankAccount.getBalance();
            double balanceNew;

            if (transactionType == TransactionType.INCOMING) {
                balanceNew = balanceOld + amount;
            } else {
                balanceNew = balanceOld - amount;
            }

            repository.saveNewBalance(bankAccountId, balanceNew);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o balanco da conta", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar o balanco da conta", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBankAccountBalance(Transaction transaction) throws BaseException {
        try {
            BankAccount bankAccount = findById(transaction.getBankAccount().getId());
            double balanceOld = bankAccount.getBalance();
            double balanceNew;
            if (transaction.getTransactionType() == TransactionType.INCOMING) {
                balanceNew = balanceOld + transaction.getAmount();
            } else {
                balanceNew = balanceOld - transaction.getAmount();
            }

            repository.saveNewBalance(bankAccount.getId(), balanceNew);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o balanco da conta", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar o balanco da conta", e);
        }
    }
}
