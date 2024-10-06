package br.com.systec.fintrack.creditcard.filter;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardSpecification {

    public static Specification<CreditCard> find(CreditCardFilterVO filterVO) {
        Specification<CreditCard> specification = Specification.where(filterByTenant());

        if(filterVO.getSearch() != null && !filterVO.getSearch().isEmpty()) {
            specification = specification.and(filterByKeyword(filterVO));
        }

        if(filterVO.getBankAccountId() != null) {
            specification.and(filterByBankAccount(filterVO));
        }

        return specification;
    }

    private static Specification<CreditCard> filterByKeyword(CreditCardFilterVO filterVO) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%"+filterVO.getSearch()+"%"));
    }

    private static Specification<CreditCard> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    public static Specification<CreditCard> filterByBankAccount(CreditCardFilterVO filterVO) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankAccount.id"), filterVO.getBankAccountId());
    }
}
