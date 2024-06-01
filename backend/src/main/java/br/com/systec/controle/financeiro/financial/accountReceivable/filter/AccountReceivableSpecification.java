package br.com.systec.controle.financeiro.financial.accountReceivable.filter;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import org.springframework.data.jpa.domain.Specification;

public class AccountReceivableSpecification {

    public static Specification<AccountReceivable> findByKeywordAndTenant(Long accountId) {
        Specification<AccountReceivable> spec = Specification.where(filterByTenant());

        if(accountId != null) {
           spec = spec.and(filterByAccountId(accountId));
        }

        return spec;
    }

    private static Specification<AccountReceivable> filterByAccountId(Long accountId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("accountId"), accountId);
        };
    }

    private static Specification<AccountReceivable> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }
}
