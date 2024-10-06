package br.com.systec.fintrack.financial.received.impl.service;

import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableException;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableNotFoundException;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.impl.repository.AccountReceivableRepository;
import br.com.systec.fintrack.financial.received.impl.repository.AccountReceivableRepositoryJPA;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
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
public class AccountReceivableServiceImpl implements AccountReceivableService {
    private static final Logger log = LoggerFactory.getLogger(AccountReceivableServiceImpl.class);
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
            accountReceivable.setTenantId(TenantContext.getTenant());
            AccountReceivable receivableAfterSave = repository.save(accountReceivable);

            updateBalanceAccountBank(receivableAfterSave);

            return receivableAfterSave;
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

    private void updateBalanceAccountBank(AccountReceivable accountReceivable) {
        bankAccountService.updateBankAccountBalance(accountReceivable.getAmount(), accountReceivable.getBankAccount().getId(), TransactionType.INCOMING);
    }

    //TODO vai ficar aqui ate resolver se vai ser por messageria ou vai ser por transação
//    private void convertAndSendJms(AccountReceivable accountReceivable) {
//        BankAccountJms bankAccountJms = new BankAccountJms(accountReceivable.getTenantId(),
//                accountReceivable.getBankAccount().getId(), accountReceivable.getAmount(), TransactionType.INCOMING);
//
//        //template.convertAndSend(RabbitMQConfig.FINANCIAL_EXCHANGE, RabbitMQConfig.ROUTING_KEY_NEW_BALANCE_ACCOUNT, bankAccountJms);
//    }
}
