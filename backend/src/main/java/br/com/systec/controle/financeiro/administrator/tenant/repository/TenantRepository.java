package br.com.systec.controle.financeiro.administrator.tenant.repository;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;
import br.com.systec.controle.financeiro.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepository extends AbstractRepository<Tenant, Long> {
}
