package br.com.systec.fintrack.invoice.vo.filter;

import br.com.systec.fintrack.commons.filter.FilterPageParam;
import br.com.systec.fintrack.invoice.model.InvoiceStatusType;

import java.time.LocalDate;

public class CreditCardInvoiceFilterParam extends FilterPageParam {

    private Long creditCardId;
    private LocalDate filterDateInitial;
    private LocalDate filterDateFinal;
    private InvoiceStatusType statusType;


    public CreditCardInvoiceFilterParam() {}

    public CreditCardInvoiceFilterParam(int pageSize, int page) {
        super(pageSize, page);
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public LocalDate getFilterDateInitial() {
        return filterDateInitial;
    }

    public void setFilterDateInitial(LocalDate filterDateInitial) {
        this.filterDateInitial = filterDateInitial;
    }

    public LocalDate getFilterDateFinal() {
        return filterDateFinal;
    }

    public void setFilterDateFinal(LocalDate filterDateFinal) {
        this.filterDateFinal = filterDateFinal;
    }

    public InvoiceStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(InvoiceStatusType statusType) {
        this.statusType = statusType;
    }


    //TODO: ver a melhor pratica para definir campos default de ordenação, gambiarra temporaria.
    @Override
    public String getSortField() {
        if(super.getSortField() == null) {
            return "dueDate";
        }
        return super.getSortField();
    }

    @Override
    public String getSortOrder() {
        if(super.getSortOrder() == null) {
            return "desc";
        }
        return super.getSortOrder();
    }
}
