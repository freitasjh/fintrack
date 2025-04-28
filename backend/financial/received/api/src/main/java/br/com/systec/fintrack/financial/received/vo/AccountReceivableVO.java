package br.com.systec.fintrack.financial.received.vo;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class AccountReceivableVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;
    private Long id;
    private BankAccount bankAccount;
    private double amount;
    private Date dateProcessed;
    private Date dateRegister;
    private TransactionType transactionType;
    private String description;
    private boolean processed;
    private Long tenantId;
    private CategoryTransactionType categoryTransactionType;
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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CategoryTransactionType getCategoryTransactionType() {
        return categoryTransactionType;
    }

    public void setCategoryTransactionType(CategoryTransactionType categoryTransactionType) {
        this.categoryTransactionType = categoryTransactionType;
    }

    public boolean isRecurringTransaction() {
        return recurringTransaction;
    }

    public void setRecurringTransaction(boolean recurringTransaction) {
        this.recurringTransaction = recurringTransaction;
    }

    public boolean isRecurringTransactionFixed() {
        return recurringTransactionFixed;
    }

    public void setRecurringTransactionFixed(boolean recurringTransactionFixed) {
        this.recurringTransactionFixed = recurringTransactionFixed;
    }

    public int getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(int totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(String frequencyType) {
        this.frequencyType = frequencyType;
    }
}
