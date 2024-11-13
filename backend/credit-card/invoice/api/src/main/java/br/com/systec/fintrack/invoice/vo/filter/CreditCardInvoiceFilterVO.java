package br.com.systec.fintrack.invoice.vo.filter;

import br.com.systec.fintrack.invoice.model.InvoiceStatusType;

import java.time.LocalDate;

public class CreditCardInvoiceFilterVO {

    private Long creditCardId;
    private LocalDate filterDateInitial;
    private LocalDate filterDateFinal;
    private InvoiceStatusType statusType;

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
}
