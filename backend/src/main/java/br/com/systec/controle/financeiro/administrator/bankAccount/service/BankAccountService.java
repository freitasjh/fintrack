package br.com.systec.controle.financeiro.administrator.bankAccount.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.exceptions.BankAccountNotFound;
import br.com.systec.controle.financeiro.administrator.bankAccount.filter.BankAccountFilterVO;
import br.com.systec.controle.financeiro.administrator.bankAccount.jms.BankAccountJmsVO;
import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.administrator.bankAccount.repository.BankAccountRepository;
import br.com.systec.controle.financeiro.administrator.bankAccount.repository.BankAccountRepositoryJPA;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableException;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import br.com.systec.controle.financeiro.financial.transaction.enums.CategoryTransactionType;
import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import br.com.systec.controle.financeiro.financial.transaction.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class BankAccountService {

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
            BankAccountJmsVO bankAccountJmsVO = new BankAccountJmsVO(bankAccount.getId(), bankAccount.getTenantId(), bankAccount.getInitialValue());

            rabbitTemplate.convertAndSend(RabbitMQConfig.FINANCIAL_EXCHANGE, RabbitMQConfig.ROUTING_KEY_NEW_BANK_ACCOUNT, bankAccountJmsVO);
        } catch (Exception e) {
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
                .orElseThrow(BankAccountNotFound::new);
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
    public void updateBankAccountBalance(Double amount, Long bankAccountId, TransactionType transactionType) {
        BankAccount bankAccount = findById(bankAccountId);
        double balanceOld = bankAccount.getBalance();
        double balanceNew;

        if (transactionType == TransactionType.INCOMING) {
            balanceNew = balanceOld + amount;
        } else {
            balanceNew = balanceOld - amount;
        }

        repository.saveNewBalance(bankAccountId, balanceNew);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBankAccountBalance(Transaction transaction) {
        BankAccount bankAccount = findById(transaction.getBankAccount().getId());
        double balanceOld = bankAccount.getBalance();
        double balanceNew;
        if (transaction.getTransactionType() == TransactionType.INCOMING) {
            balanceNew = balanceOld + transaction.getAmount();
        } else {
            balanceNew = balanceOld - transaction.getAmount();
        }

        repository.saveNewBalance(bankAccount.getId(), balanceNew);
    }
}
