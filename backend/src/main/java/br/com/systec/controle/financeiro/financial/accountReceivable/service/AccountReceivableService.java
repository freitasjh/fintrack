package br.com.systec.controle.financeiro.financial.accountReceivable.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.jms.BankAccountJms;
import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableException;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableNotFoundException;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepositoryJPA;
import br.com.systec.controle.financeiro.financial.accountTransfer.service.AccountTransferService;
import br.com.systec.controle.financeiro.financial.transaction.enums.CategoryTransactionType;
import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(AccountTransferService.class);
    @Autowired
    private AccountReceivableRepository repository;
    @Autowired
    private AccountReceivableRepositoryJPA repositoryJPA;
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private BankAccountService bankAccountService;

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountReceivable save(AccountReceivable accountReceivable) throws AccountReceivableException {
        try{
            AccountReceivable accountReceivableSaved = repository.save(accountReceivable);

            updateBalanceAccountBank(accountReceivableSaved);

            return accountReceivableSaved;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o recebimento", e);
            throw new AccountReceivableException("Ocorreu um erro ao tentar salvar o recebimento", e);
        }
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

//    @Transactional(propagation = Propagation.REQUIRED)
//    public void delete(Long id) {
//        repository.deleteById(id);
//    }

    private void updateBalanceAccountBank(AccountReceivable accountReceivable) {
        bankAccountService.updateBankAccountBalance(accountReceivable);
    }

    //TODO vai ficar aqui ate resolver se vai ser por messageria ou vai ser por transação
//    private void convertAndSendJms(AccountReceivable accountReceivable) {
//        BankAccountJms bankAccountJms = new BankAccountJms(accountReceivable.getTenantId(),
//                accountReceivable.getBankAccount().getId(), accountReceivable.getAmount(), TransactionType.INCOMING);
//
//        //template.convertAndSend(RabbitMQConfig.FINANCIAL_EXCHANGE, RabbitMQConfig.ROUTING_KEY_NEW_BALANCE_ACCOUNT, bankAccountJms);
//    }
}
