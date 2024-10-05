package br.com.systec.fintrack.financial.payment.filter;

import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import org.springframework.data.jpa.domain.Specification;

public class AccountPaymentPageParam extends PageParamSearchVO<AccountPayment> {
    private AccountPaymentFilterVO filterVO;

    public AccountPaymentPageParam(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<AccountPayment> getSpecification() {
        return AccountPaymentSpecification.filter(filterVO);
    }

    public AccountPaymentFilterVO getFilterVO() {
        if(filterVO == null){
            filterVO = new AccountPaymentFilterVO();
        }
        return filterVO;
    }

    public void setFilterVO(AccountPaymentFilterVO filterVO) {
        this.filterVO = filterVO;
    }
}
