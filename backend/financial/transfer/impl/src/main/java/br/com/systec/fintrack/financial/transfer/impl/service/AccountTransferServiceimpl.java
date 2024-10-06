package br.com.systec.fintrack.financial.transfer.impl.service;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;
import br.com.systec.fintrack.financial.transfer.exceptions.AccountTransferFindException;
import br.com.systec.fintrack.financial.transfer.filters.AccountTransferFilterVO;
import br.com.systec.fintrack.financial.transfer.impl.repository.AccountTransferRepository;
import br.com.systec.fintrack.financial.transfer.impl.repository.AccountTransferRepositoryJPA;
import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;
import br.com.systec.fintrack.financial.transfer.service.AccountTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountTransferServiceimpl implements AccountTransferService {
    private static final Logger log = LoggerFactory.getLogger(AccountTransferServiceimpl.class);
    @Autowired
    private AccountTransferRepository repository;
    @Autowired
    private AccountTransferRepositoryJPA repositoryJPA;
    @Autowired
    private AccountPaymentService accountPaymentService;
    @Autowired
    private AccountReceivableService accountReceivableService;
    @Autowired
    private BankAccountService bankAccountService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AccountTransfer save(AccountTransfer accountTransfer) throws BaseException {
        try {
            BankAccount bankAccountFromReturn = bankAccountService.findById(accountTransfer.getBankAccountFrom().getId());

            //valida se o banco tem saldo para realizar a transferencia...
            if (bankAccountFromReturn.getBalance() < accountTransfer.getAmount()) {
                throw new BaseException("Saldo insuficiente para realizar a transferencia");
            }

            BankAccount bankAccountToReturn = bankAccountService.findById(accountTransfer.getBankAccountTo().getId());
            accountTransfer.setBankAccountFrom(bankAccountFromReturn);
            accountTransfer.setBankAccountTo(bankAccountToReturn);

            AccountTransfer accountTransferSaved = repository.save(accountTransfer);

            saveAccountPayment(accountTransferSaved);
            saveAccountReceiver(accountTransferSaved);

            return accountTransferSaved;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar fazer a transferencia", e);
            throw new BaseException("Ocorreu um erro ao tentar fazer a transferencia", e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountTransfer> findAll() throws AccountTransferFindException {
        try {
            return repository.findAllByTenant();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar realizar a pesquisa", e);
            throw new AccountTransferFindException("Ocorreu um erro ao tentar realizar a pesquisa",e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<AccountTransfer> findByFilter(AccountTransferFilterVO filterVO) throws AccountTransferFindException {
        try {
           Page<AccountTransfer> pageFilterAccountTransfer = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());
            return pageFilterAccountTransfer;
        } catch (Exception e) {
            log.error("Erro ao tentar realizar a pesquisa", e);
            throw new AccountTransferFindException("Occoreu um erro ao tentar realizar a pesquisa", e);
        }
    }


    private void saveAccountPayment(AccountTransfer accountTransfer) throws BaseException {
        AccountPayment accountPayment = new AccountPayment();
        accountPayment.setTenantId(TenantContext.getTenant());
        accountPayment.setDescription("Transferencia para "+accountTransfer.getBankAccountTo().getDescription());
        accountPayment.setBankAccount(accountTransfer.getBankAccountFrom());
        accountPayment.setDateRegister(accountTransfer.getTransferDate());
        accountPayment.setDateProcessed(accountTransfer.getTransferDate());
        accountPayment.setCategoryTransactionType(CategoryTransactionType.TRANSFER);
        accountPayment.setTransactionType(TransactionType.EXPENSE);
        accountPayment.setAmount(accountTransfer.getAmount());
        accountPayment.setProcessed(true);

        accountPaymentService.save(accountPayment);
    }

    private void saveAccountReceiver(AccountTransfer accountTransfer) throws BaseException {
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setTenantId(TenantContext.getTenant());
        accountReceivable.setDescription("Transferencia de "+accountTransfer.getBankAccountFrom().getDescription());
        accountReceivable.setBankAccount(accountTransfer.getBankAccountTo());
        accountReceivable.setDateRegister(accountTransfer.getTransferDate());
        accountReceivable.setDateProcessed(accountTransfer.getTransferDate());
        accountReceivable.setCategoryTransactionType(CategoryTransactionType.TRANSFER);
        accountReceivable.setTransactionType(TransactionType.INCOMING);
        accountReceivable.setAmount(accountTransfer.getAmount());
        accountReceivable.setProcessed(true);

        accountReceivableService.save(accountReceivable);
    }
}