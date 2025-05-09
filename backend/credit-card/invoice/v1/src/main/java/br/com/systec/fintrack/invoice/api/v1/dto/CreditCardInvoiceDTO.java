package br.com.systec.fintrack.invoice.api.v1.dto;

import br.com.systec.fintrack.invoice.model.InvoiceStatusType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCardInvoiceDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5840496973086789675L;
    private Long id;
    private String creditCardName;
    private LocalDate dateClose;
    private LocalDate dueDate;
    private InvoiceStatusType statusType;
    private double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
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

    public InvoiceStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(InvoiceStatusType statusType) {
        this.statusType = statusType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditCardInvoiceDTO{" +
                "id=" + id +
                ", creditCardName='" + creditCardName + '\'' +
                ", dateClose=" + dateClose +
                ", dueDate=" + dueDate +
                ", statusType=" + statusType +
                ", amount=" + amount +
                '}';
    }
}
