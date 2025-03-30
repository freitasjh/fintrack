package br.com.systec.fintrack.creditcard.transaction.filter;

import br.com.systec.fintrack.commons.filter.FilterPageParam;


public class CreditCardTransactionPageParam extends FilterPageParam {

    private CreditCardTransactionFilterVO filterVO;

    public CreditCardTransactionPageParam(int pageSize, int page) {
        super(pageSize, page);
    }

    public CreditCardTransactionFilterVO getFilterVO() {
        return filterVO;
    }

    public void setFilterVO(CreditCardTransactionFilterVO filterVO) {
        this.filterVO = filterVO;
    }
}
