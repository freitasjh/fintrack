package br.com.systec.fintrack.financial.received.filter;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import org.springframework.data.jpa.domain.Specification;

public class AccountReceivableSpecification {

    public static Specification<AccountReceivable> findByKeywordAndTenant(Long bankAccountId) {
        Specification<AccountReceivable> spec = Specification.where(filterByTenant())
                .and(filterByType());

        if(bankAccountId != null) {
           spec = spec.and(filterByAccountId(bankAccountId));
        }

        return spec;
    }

    private static Specification<AccountReceivable> filterByAccountId(Long bankAccountId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("bankAccount"), new BankAccount(bankAccountId));
        };
    }

    private static Specification<AccountReceivable> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<AccountReceivable> filterByType() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("transactionType"), TransactionType.INCOMING);
    }
}
