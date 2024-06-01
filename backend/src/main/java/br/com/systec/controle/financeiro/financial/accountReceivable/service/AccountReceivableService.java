package br.com.systec.controle.financeiro.financial.accountReceivable.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountReceivableService {

    @Autowired
    private AccountReceivableRepository repository;
    @Autowired
    private AccountReceivableRepositoryJPA repositoryJPA;

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountReceivable save(AccountReceivable accountReceivable) {
        if(accountReceivable.getTenantId() == null){
            accountReceivable.setTenantId(TenantContext.getTenant());
        }
        return repository.save(accountReceivable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountReceivable update(AccountReceivable accountReceivable){
        return repository.update(accountReceivable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        AccountReceivable accountReceivable = findById(id);

        repository.delete(accountReceivable);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AccountReceivable findAccountReceivableById(Long id){
        return findById(id);
    }

    private AccountReceivable findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(I18nTranslate.toLocale("accountReceivable.not.found")));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<AccountReceivable> findByFilter(AccountReceivableFilterVO filterVO) {
        return repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable());
    }
}
