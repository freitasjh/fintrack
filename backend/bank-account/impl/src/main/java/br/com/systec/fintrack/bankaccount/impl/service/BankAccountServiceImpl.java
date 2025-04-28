package br.com.systec.fintrack.bankaccount.impl.service;

import br.com.systec.fintrack.bankaccount.exception.BankAccountNotFoundException;
import br.com.systec.fintrack.bankaccount.filter.BankAccountFilterVO;
import br.com.systec.fintrack.bankaccount.impl.jms.BankAccountJmsVO;
import br.com.systec.fintrack.bankaccount.impl.repository.BankAccountRepository;
import br.com.systec.fintrack.bankaccount.impl.repository.BankAccountRepositoryJPA;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.bankaccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.config.I18nTranslate;
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
public class BankAccountServiceImpl implements BankAccountService {
    private static final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    @Autowired
    private BankAccountRepository repository;
    @Autowired
    private BankAccountRepositoryJPA repositoryJPA;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccount save(BankAccount bankAccount) throws BaseException {
        if (bankAccount.getTenantId() == null) {
            bankAccount.setTenantId(TenantContext.getTenant());
        }

        BankAccount bankAccountSaved = repository.save(bankAccount);

        if (bankAccount.getInitialValue() > 0.0) {
            saveAmountInitialAccount(bankAccountSaved);
        }

        return bankAccountSaved;
    }

    private void saveAmountInitialAccount(BankAccount bankAccount) throws BaseException {
        try {
            BankAccountJmsVO bankAccountJmsVO = new BankAccountJmsVO(TenantContext.getTenant(), bankAccount.getId(), bankAccount.getInitialValue());

            rabbitTemplate.convertAndSend(RabbitMQConstants.FINANCIAL_EXCHANGE, RabbitMQConstants.ROUTING_KEY_NEW_BANK_ACCOUNT, bankAccountJmsVO);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar enviar a mensagem para salvar o valor inicial", e);
            throw new BaseException(I18nTranslate.toLocale("error.save.account.opening.balance"));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccount update(BankAccount bankAccount) throws BaseException {
        if (bankAccount.getTenantId() == null) {
            bankAccount.setTenantId(TenantContext.getTenant());
        }

        return repository.update(bankAccount);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BankAccount findById(Long id) throws BaseException {
        return findAccountById(id);
    }

    private BankAccount findAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(BankAccountNotFoundException::new);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<BankAccount> findByFilter(BankAccountFilterVO filterVO) throws BaseException {
        Page<BankAccount> listPageBankAccount = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());

        return listPageBankAccount;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findCurrentAccountBalance() throws BaseException {
        return repository.findCurrentAccountBalance();
    }


    @Override
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
}
