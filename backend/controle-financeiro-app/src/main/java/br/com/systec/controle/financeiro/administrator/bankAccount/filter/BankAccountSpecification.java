package br.com.systec.controle.financeiro.administrator.bankAccount.filter;

import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.commons.TenantContext;
import org.springframework.data.jpa.domain.Specification;

public class BankAccountSpecification {


    public static Specification<BankAccount> filterByKeywordAndTenant(String filter) {
        Specification<BankAccount> spec = Specification.where(filterByTenant());

        if (filter != null && !filter.isEmpty()) {
            spec = spec.and(filterByKeyword(filter));
        }

        return spec;
    }

    private static Specification<BankAccount> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<BankAccount> filterByKeyword(String filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("description"), "%" + filter + "%"),
                        criteriaBuilder.like(root.get("agency"), "%" + filter + "%"),
                        criteriaBuilder.like(root.get("account"), "%" + filter + "%")
                );
    }

}


