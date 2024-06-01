package br.com.systec.controle.financeiro.administrator.bankAccount.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.filter.BankAccountFilterVO;
import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.administrator.bankAccount.repository.BankAccountRepository;
import br.com.systec.controle.financeiro.administrator.bankAccount.repository.BankAccountRepositoryJPA;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableException;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository repository;
    @Autowired
    private BankAccountRepositoryJPA repositoryJPA;
    @Autowired
    private AccountReceivableService receiveService;

    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccount save(BankAccount bankAccount) {
        if(bankAccount.getTenantId() == null) {
            bankAccount.setTenantId(TenantContext.getTenant());
        }

        BankAccount bankAccountSaved = repository.save(bankAccount);

        if(bankAccount.getBalance() > 0.0) {
            saveAmountInitialAccount(bankAccountSaved);
        }

        return bankAccountSaved;
    }

    private void saveAmountInitialAccount(BankAccount bankAccount){
        try {
            AccountReceivable receive = new AccountReceivable();
            receive.setDateReceiver(LocalDateTime.now());
            receive.setDateRegister(LocalDateTime.now());
            receive.setAccountId(bankAccount.getId());
            receive.setDescription(I18nTranslate.toLocale("account.opening.balance"));
            receive.setAmount(bankAccount.getBalance());
            receive.setTenantId(bankAccount.getTenantId());

            receiveService.save(receive);
        }catch (Exception e) {
            throw new AccountReceivableException(I18nTranslate.toLocale("error.save.account.opening.balance"));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BankAccount update(BankAccount bankAccount) {
        if(bankAccount.getTenantId() == null) {
            bankAccount.setTenantId(TenantContext.getTenant());
        }

        return repository.update(bankAccount);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BankAccount findById(Long id) {
        BankAccount bankAccount = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada"));

        return bankAccount;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<BankAccount> findByFilter(BankAccountFilterVO filterVO) {
        Page<BankAccount> listPageBankAccount = repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());

        return listPageBankAccount;
    }
}
