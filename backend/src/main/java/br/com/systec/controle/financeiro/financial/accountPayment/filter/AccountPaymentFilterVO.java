package br.com.systec.controle.financeiro.financial.accountPayment.filter;

import br.com.systec.controle.financeiro.commons.filter.FilterSearchVO;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import org.springframework.data.jpa.domain.Specification;

public class AccountPaymentFilterVO extends FilterSearchVO<AccountPayment> {
    private Long accountId;


    public AccountPaymentFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<AccountPayment> getSpecification() {
        return AccountPaymentSpecification.findByKeywordAndTenant(accountId);
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
