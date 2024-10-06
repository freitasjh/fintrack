package br.com.systec.fintrack.tenant.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.tenant.model.Tenant;

public interface TenantService {

    Tenant save(Tenant tenant) throws BaseException;
    Tenant update(Long tenantId, Long userPrincipalId) throws BaseException;
    Tenant findTenantById(Long tenantId) throws ObjectNotFoundException;
}
