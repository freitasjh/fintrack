package br.com.systec.fintrack.creditcard.transaction.filter;

import br.com.systec.fintrack.commons.filter.FilterParamVO;

import java.io.Serial;
import java.time.LocalDate;

public class CreditCardTransactionFilterVO extends FilterParamVO {
    @Serial
    private static final long serialVersionUID = 5272080917537826078L;

    private Long creditCardId;
    private LocalDate dateFilter;

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public LocalDate getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(LocalDate dateFilter) {
        this.dateFilter = dateFilter;
    }
}
