package br.com.systec.fintrack.financial.payment.filter;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.commons.utils.DateUtils;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import org.springframework.data.jpa.domain.Specification;

public class AccountPaymentSpecification {

    public static Specification<AccountPayment> filter(AccountPaymentFilterVO filterVO) {
        Specification<AccountPayment> spec = Specification.where(filterByTenant())
                .and(filterByType());

        if (filterVO.getBankAccountId() != null) {
            spec = spec.and(filterByAccountId(filterVO.getBankAccountId()));
        }

        if (filterVO.getFilterType() != AccountPaymentFilterType.TODOS) {
            boolean isPaymentOpen = !(filterVO.getFilterType() == AccountPaymentFilterType.ABERTO);
            spec = spec.and(filterByPaymentOpenOrNot(isPaymentOpen));
        }

        if (filterVO.getDateProcessedInitial() != null) {
           spec = filterByDateProcessed(filterVO, spec);
        }

        return spec;
    }

    public static Specification<AccountPayment> filterByDateProcessed(AccountPaymentFilterVO filterVO, Specification<AccountPayment> specification) {
        if (filterVO.getDateProcessedFinal() != null) {
            return specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(
                            root.get("dateProcessed"),
                            DateUtils.converterLocalDateToDate(filterVO.getDateProcessedInitial()),
                            DateUtils.converterLocalDateToDate(filterVO.getDateProcessedFinal())
                    )
            );
        }

        return specification.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dateProcessed"), DateUtils.converterLocalDateToDate(filterVO.getDateProcessedInitial()))
        );
    }

    public static Specification<AccountPayment> filterByDatePayment(AccountPaymentFilterVO filterVO) {
        return null;
    }

    public static Specification<AccountPayment> filterByPaymentOpenOrNot(boolean isOpen) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("processed"), isOpen);
    }

    private static Specification<AccountPayment> filterByAccountId(Long bankAccountId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bankAccount.id"), bankAccountId);
    }

    private static Specification<AccountPayment> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<AccountPayment> filterByType() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("transactionType"), TransactionType.EXPENSE);
    }
}

