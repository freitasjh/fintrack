package br.com.systec.controle.financeiro.financial.accountTransfer.filter;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountTransfer.model.AccountTransfer;
import org.springframework.data.jpa.domain.Specification;

public class AccountTransferSpecification {

    public static Specification<AccountTransfer> find(Long bankAccountFromId, Long bankAccountToId) {
        Specification<AccountTransfer> specification = Specification.where(filterByTenant());

        if (bankAccountToId != null) {
            specification = specification.and(filterByBankAccountTo(bankAccountToId));
        }

        if (bankAccountFromId != null) {
            specification = specification.and(filterByBankAccountFrom(bankAccountFromId));
        }

        return specification;
    }

    private static Specification<AccountTransfer> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<AccountTransfer> filterByBankAccountFrom(Long bankAccountFromId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankAccountFrom.id"), bankAccountFromId);
    }

    private static Specification<AccountTransfer> filterByBankAccountTo(Long bankAccountToId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankAccountTo.id"), bankAccountToId);
    }


}
