package br.com.systec.fintrack.financial.received.api.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class AccountReceivableInputDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;
    private Long id;
    @NotNull(message = "Informe uma descrição")
    private String description;
    private double amount;
    private boolean processed;
    private Date dateRegister;
    private Date dateProcessed;
    @NotNull(message = "Infome a conta bancaria")
    private Long bankAccountId;
    private boolean recurringTransaction;
    private boolean recurringTransactionFixed;
    private int totalInstallments;
    private String frequencyType;


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

    public int getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(int totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setRecurringTransactionFixed(boolean recurringTransactionFixed) {
        this.recurringTransactionFixed = recurringTransactionFixed;
    }

    public boolean isRecurringTransactionFixed() {
        return recurringTransactionFixed;
    }

    public void setRecurringTransaction(boolean recurringTransaction) {
        this.recurringTransaction = recurringTransaction;
    }

    public boolean isRecurringTransaction() {
        return recurringTransaction;
    }
}
