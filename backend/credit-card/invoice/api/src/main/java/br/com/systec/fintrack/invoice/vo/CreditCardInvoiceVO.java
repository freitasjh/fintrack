package br.com.systec.fintrack.invoice.vo;

import br.com.systec.fintrack.invoice.model.InvoiceStatusType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCardInvoiceVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4575058838022315188L;
    private final Long id;
    private final LocalDate dueDate;
    private final InvoiceStatusType statusType;
    private final String creditCardDescription;
    private final double amount;

    public CreditCardInvoiceVO(Long id, LocalDate dueDate, InvoiceStatusType statusType, String creditCardDescription, double amount) {
        this.id = id;
        this.dueDate = dueDate;
        this.statusType = statusType;
        this.creditCardDescription = creditCardDescription;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public InvoiceStatusType getStatusType() {
        return statusType;
    }

    public String getCreditCardDescription() {
        return creditCardDescription;
    }

    public double getAmount() {
        return amount;
    }
}
