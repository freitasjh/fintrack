package br.com.systec.fintrack.financial.received.filter;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.commons.utils.DateUtils;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import org.springframework.data.jpa.domain.Specification;

public class AccountReceivableSpecification {


    public static Specification<AccountReceivable> filter(AccountReceivableFilterVO filter) {
        Specification<AccountReceivable> spec = Specification.where(filterByTenant())
                .and(filterByType());

        if(filter.getAccountId() != null) {
            spec = spec.and(filterByAccountId(filter));
        }

        if(filter.getDateProcessed() != null) {
            spec = spec.and(filterByDateRegister(filter));
        }

        return spec;
    }

    private static Specification<AccountReceivable> filterByDateRegister(AccountReceivableFilterVO filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dateProcessed"), DateUtils.converterLocalDateToDate(filter.getDateProcessed()));
    }

    private static Specification<AccountReceivable> filterByAccountId(AccountReceivableFilterVO filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankAccount"), new BankAccount(filter.getAccountId()));
    }

    private static Specification<AccountReceivable> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<AccountReceivable> filterByType() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("transactionType"), TransactionType.INCOMING);
    }
}
