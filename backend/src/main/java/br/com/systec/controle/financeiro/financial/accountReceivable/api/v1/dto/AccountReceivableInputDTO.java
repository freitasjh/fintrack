package br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class AccountReceivableInputDTO {

    private Long id;
    @NotNull(message = "Informe uma descrição")
    private String description;
    private double amount;
    private boolean processed;
    private Date dateRegister;
    private Date dateProcessed;
    @NotNull(message = "Infome a conta bancaria")
    private Long bankAccountId;

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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
