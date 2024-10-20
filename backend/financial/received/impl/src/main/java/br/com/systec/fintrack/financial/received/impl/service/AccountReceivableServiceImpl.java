package br.com.systec.fintrack.financial.received.impl.service;

import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableException;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableNotFoundException;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.impl.mapper.AccountReceivableMapper;
import br.com.systec.fintrack.financial.received.impl.repository.AccountReceivableRepository;
import br.com.systec.fintrack.financial.received.impl.repository.AccountReceivableRepositoryJPA;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import br.com.systec.fintrack.financial.recurringtransaction.model.FrequencyType;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.service.RecurringTransactionService;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;
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
    @Autowired
    private RecurringTransactionService recurringTransactionService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AccountReceivableVO save(AccountReceivableVO accountReceivableVO) throws AccountReceivableException {
        try{
            AccountReceivable accountReceivable = AccountReceivableMapper.toEntity(accountReceivableVO);
            accountReceivable.setTenantId(TenantContext.getTenant());

            AccountReceivable accountReceivableAfterSave = repository.save(accountReceivable);
            accountReceivableVO.setId(accountReceivableAfterSave.getId());

            updateBalanceAccountBank(accountReceivableAfterSave);

            if(accountReceivableVO.isRecurringTransaction()) {
                createRecurringTransaction(accountReceivableVO);
            }

            return AccountReceivableMapper.toVO(accountReceivableAfterSave);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o recebimento", e);
            throw new AccountReceivableException("Ocorreu um erro ao tentar salvar o recebimento", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountReceivableVO> findAll() {
        List<AccountReceivable> listOfAccountReceivable = repository.findAll(TenantContext.getTenant());

        return AccountReceivableMapper.toListVO(listOfAccountReceivable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<AccountReceivableVO> findByFilter(AccountReceivableFilterVO filterVO) {
        Page<AccountReceivable> pageOfAccountReceivable = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());

        return AccountReceivableMapper.toPageVO(pageOfAccountReceivable);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AccountReceivableVO findById(Long id) {
        AccountReceivable accountReceivable = repository.findById(id).orElseThrow(AccountReceivableNotFoundException::new);

        return AccountReceivableMapper.toVO(accountReceivable);
    }

    private void updateBalanceAccountBank(AccountReceivable accountReceivable) {
        if(accountReceivable.isProcessed()) {
            bankAccountService.updateBankAccountBalance(accountReceivable.getAmount(), accountReceivable.getBankAccount().getId(), TransactionType.INCOMING);
        }
    }

    private void createRecurringTransaction(AccountReceivableVO accountReceivableVO) throws BaseException {
        log.info("@@ Salvando Transação recorrente");

        RecurringTransactionVO recurringTransaction = new RecurringTransactionVO();
        recurringTransaction.setTransactionId(accountReceivableVO.getId());
        recurringTransaction.setTransactionType(TransactionType.INCOMING);
        recurringTransaction.setTransactionFixed(accountReceivableVO.isRecurringTransactionFixed());
        recurringTransaction.setTotalInstallments(accountReceivableVO.getTotalInstallments());
        recurringTransaction.setFrequencyType(FrequencyType.valueOf(accountReceivableVO.getFrequencyType()));
        recurringTransaction.setCurrentInstallments(0);

        recurringTransactionService.create(recurringTransaction);
    }
}
