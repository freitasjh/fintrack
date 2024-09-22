package br.com.systec.fintrack.administrator.tenant.service;

import br.com.systec.fintrack.administrator.tenant.model.Tenant;
import br.com.systec.fintrack.administrator.tenant.repository.TenantRepository;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService {

    @Autowired
    private TenantRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Tenant save(Tenant tenant) throws BaseException {
        Tenant tenantSave = repository.save(tenant);

        return tenantSave;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tenant update(Long tenantId, Long userPrincipalId) throws BaseException {
        Tenant tenant = findTenantById(tenantId);
        tenant.setUserPrincipalId(userPrincipalId);

        return repository.update(tenant);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Tenant findTenantById(Long tenantId) throws ObjectNotFoundException {
        Tenant tenantReturn = repository.findById(tenantId).orElseThrow(() -> new ObjectNotFoundException("Tenant não encontrado"));

        return tenantReturn;
    }
}
