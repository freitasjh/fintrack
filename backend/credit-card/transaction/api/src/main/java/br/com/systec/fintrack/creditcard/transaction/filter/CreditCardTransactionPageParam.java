package br.com.systec.fintrack.creditcard.transaction.filter;

import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardTransactionPageParam extends PageParamSearchVO<CreditCardTransaction> {

    private CreditCardTransactionFilterVO filterVO;

    public CreditCardTransactionPageParam(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<CreditCardTransaction> getSpecification() {
        return CreditCardTransactionSpecification.filter(getFilterVO());
    }

    public CreditCardTransactionFilterVO getFilterVO() {
        if(filterVO == null) {
            filterVO = new CreditCardTransactionFilterVO();
        }
        return filterVO;
    }

    public void setFilterVO(CreditCardTransactionFilterVO filterVO) {
        this.filterVO = filterVO;
    }
}
