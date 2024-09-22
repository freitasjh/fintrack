package br.com.systec.controle.financeiro.financial.accountPayment.filter;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;

public class AccountPaymentSpecification {

    public static Specification<AccountPayment> filter(AccountPaymentFilterVO filterVO) {
        Specification<AccountPayment> spec = Specification.where(filterByTenant())
                .and(filterByType());

        if(filterVO.getBankAccountId() != null) {
            spec = spec.and(filterByAccountId(filterVO.getBankAccountId()));
        }

        if (filterVO.getFilterType() != AccountPaymentFilterType.TODOS) {
            boolean isPaymentOpen = !(filterVO.getFilterType() == AccountPaymentFilterType.ABERTO);
            spec = spec.and(filterByPaymentOpenOrNot(isPaymentOpen));
        }

        return spec;
    }


    public static Specification<AccountPayment> filterByPaymentOpenOrNot(boolean isOpen) {
        return Specification.where(filterByTenant())
                .and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("processed"), isOpen));
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
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("transactionType"), TransactionType.EXPENSE);
    }
}

