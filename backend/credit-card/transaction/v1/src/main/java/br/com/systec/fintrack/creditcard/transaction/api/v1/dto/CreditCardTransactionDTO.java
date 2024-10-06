package br.com.systec.fintrack.creditcard.transaction.api.v1.dto;

import java.time.LocalDate;

public class CreditCardTransactionDTO {

    private Long id;
    private String description;
    private LocalDate dateCreate;
    private String creditCardName;
    private double amount;
    private int installments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
        this.creditCardName = creditCardName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    @Override
    public String toString() {
        return "CreditCardTransactionDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateCreate=" + dateCreate +
                ", creditCardName='" + creditCardName + '\'' +
                ", amount=" + amount +
                ", installments=" + installments +
                '}';
    }
}
