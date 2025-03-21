package br.com.systec.fintrack.invoice.api.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCardInvoiceInstalmentResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6258314938470013420L;

    private String description;
    private double amount;
    private LocalDate dateCreate;
    private int installment;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    @Override
    public String toString() {
        return "CreditCardInvoiceInstalmentResponseDTO{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", dateCreate=" + dateCreate +
                ", installment=" + installment +
                '}';
    }
}
