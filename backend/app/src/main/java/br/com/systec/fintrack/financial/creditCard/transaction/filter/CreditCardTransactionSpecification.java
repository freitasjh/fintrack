package br.com.systec.fintrack.financial.creditCard.transaction.filter;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.creditCard.transaction.model.CreditCardTransaction;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardTransactionSpecification {

    public static Specification<CreditCardTransaction> filter(CreditCardTransactionFilterVO filterVO) {
        Specification<CreditCardTransaction> spec = Specification.where(filterByTenant());


        if(filterVO.getCreditCardId() != null) {
            spec = spec.and(filterByCreditCard(filterVO));
        }

        if (filterVO.getDateFilter() != null) {
            spec = spec.and(filterByDate(filterVO));
        }

        return spec;
    }

    private static Specification<CreditCardTransaction> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<CreditCardTransaction> filterByCreditCard(CreditCardTransactionFilterVO filterVO) {
        return  (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("creditCard.id"), filterVO.getCreditCardId());
    }

    private static Specification<CreditCardTransaction> filterByDate(CreditCardTransactionFilterVO filterVO) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dateCreated"), filterVO.getDateFilter());
    }
}
