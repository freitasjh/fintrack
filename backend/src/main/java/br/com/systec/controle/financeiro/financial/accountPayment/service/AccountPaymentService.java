package br.com.systec.controle.financeiro.financial.accountPayment.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.jms.BankAccountJms;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.financial.accountPayment.filter.AccountPaymentFilterVO;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.accountPayment.repository.AccountPaymentRepository;
import br.com.systec.controle.financeiro.financial.accountPayment.repository.AccountPaymentRepositoryJpa;
import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountPaymentService {
    private static final Logger log = LoggerFactory.getLogger(AccountPayment.class);

    @Autowired
    private AccountPaymentRepository repository;
    @Autowired
    private AccountPaymentRepositoryJpa repositoryJpa;
    @Autowired
    private RabbitTemplate template;

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountPayment save(AccountPayment accountPayment) {
        AccountPayment accountPaymentSaved = repository.save(accountPayment);

        convertAndSendJmsBankAccount(accountPayment);

        return accountPaymentSaved;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountPayment update(AccountPayment accountPayment) {
        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AccountPayment> listAllPayment() {
        return repository.findAllByTenant();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<AccountPayment> findPaymentByFilter(AccountPaymentFilterVO filterVO) {
        Page<AccountPayment> pageOfAccountPayment = repositoryJpa.findAll(filterVO.getSpecification(), filterVO.getPageable());

        return pageOfAccountPayment;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findMonthlyExpenses() {
        return repository.findMonthlyExpenses();
    }

    private void convertAndSendJmsBankAccount(AccountPayment accountPayment){
        BankAccountJms bankAccountJms = new BankAccountJms(accountPayment.getTenantId(),
                accountPayment.getBankAccount().getId(), accountPayment.getAmount(), TransactionType.EXPENSE);

        template.convertAndSend(RabbitMQConfig.FINANCIAL_EXCHANGE, RabbitMQConfig.ROUTING_KEY_NEW_BALANCE_ACCOUNT, bankAccountJms);
    }
}
