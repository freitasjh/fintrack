package br.com.systec.controle.financeiro.financial.creditCard.transaction.api.v1.dto;

import jakarta.validation.constraints.NotNull;

public class CreditCardTransactionInputDTO {

    private Long id;
    @NotNull(message = "Informe uma descrição")
    private String description;
    private double amount;
    private int installments;
    @NotNull(message = "Infome o cartão que foi realizado a compra")
    private Long creditCardId;

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

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    @Override
    public String toString() {
        return "CreditCardTransactionInputDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", installments=" + installments +
                ", creditCardId=" + creditCardId +
                '}';
    }
}
