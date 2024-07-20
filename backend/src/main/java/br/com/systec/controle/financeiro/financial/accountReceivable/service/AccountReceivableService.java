package br.com.systec.controle.financeiro.financial.accountReceivable.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.jms.BankAccountJms;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableNotFoundException;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepositoryJPA;
import br.com.systec.controle.financeiro.financial.transaction.enums.CategoryTransactionType;
import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountReceivableService {

    @Autowired
    private AccountReceivableRepository repository;
    @Autowired
    private AccountReceivableRepositoryJPA repositoryJPA;
    @Autowired
    private RabbitTemplate template;

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountReceivable save(AccountReceivable accountReceivable) {
        if(accountReceivable.getTenantId() == null){
            accountReceivable.setTenantId(TenantContext.getTenant());
        }

        AccountReceivable accountReceivableSaved = repository.save(accountReceivable);

        convertAndSendJms(accountReceivable);

        return accountReceivableSaved;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountReceivable update(AccountReceivable accountReceivable) {

        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountReceivable> findAll() {
        return repository.findAll(TenantContext.getTenant());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<AccountReceivable> findByFilter(AccountReceivableFilterVO filterVO) {
        Page<AccountReceivable> pageOfAccountReceivable = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());

        return pageOfAccountReceivable;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AccountReceivable findById(Long id) {
        return repository.findById(id).orElseThrow(AccountReceivableNotFoundException::new);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repository.deleteById(id);
    }


    private void convertAndSendJms(AccountReceivable accountReceivable) {
        BankAccountJms bankAccountJms = new BankAccountJms(accountReceivable.getTenantId(),
                accountReceivable.getBankAccount().getId(), accountReceivable.getAmount(), TransactionType.INCOMING);

        template.convertAndSend(RabbitMQConfig.FINANCIAL_EXCHANGE, RabbitMQConfig.ROUTING_KEY_NEW_BALANCE_ACCOUNT, bankAccountJms);
    }
}
