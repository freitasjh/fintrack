package br.com.systec.fintrack.administrator.tenant.repository;

import br.com.systec.fintrack.administrator.tenant.model.Tenant;
import br.com.systec.fintrack.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepository extends AbstractRepository<Tenant, Long> {
}
