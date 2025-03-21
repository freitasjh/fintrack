package br.com.systec.fintrack.invoice.api.v1.dto;

import br.com.systec.fintrack.invoice.model.InvoiceStatusType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCardInvoiceInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5117136588835004332L;

    private Long id;
    private LocalDate dateClose;
    private LocalDate dueDate;
    private double amount;
    private InvoiceStatusType invoiceStatusType;
    private String creditCardDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public InvoiceStatusType getInvoiceStatusType() {
        return invoiceStatusType;
    }

    public void setInvoiceStatusType(InvoiceStatusType invoiceStatusType) {
        this.invoiceStatusType = invoiceStatusType;
    }

    public String getCreditCardDescription() {
        return creditCardDescription;
    }

    public void setCreditCardDescription(String creditCardDescription) {
        this.creditCardDescription = creditCardDescription;
    }
}
