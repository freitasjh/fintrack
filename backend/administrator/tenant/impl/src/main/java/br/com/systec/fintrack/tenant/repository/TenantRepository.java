package br.com.systec.fintrack.tenant.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.tenant.model.Tenant;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepository extends AbstractRepository<Tenant, Long> {
}
