package br.com.systec.fintrack.financial.recurringtransaction.vo;

import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.recurringtransaction.model.FrequencyType;

import java.io.Serial;
import java.io.Serializable;

public class RecurringTransactionVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;
    private Long id;
    private Long transactionId;
    private TransactionType transactionType;
    private FrequencyType frequencyType;
    private int totalInstallments;
    private int currentInstallments;
    private boolean transactionFixed;
    private Long tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public FrequencyType getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(FrequencyType frequencyType) {
        this.frequencyType = frequencyType;
    }

    public int getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(int totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public int getCurrentInstallments() {
        return currentInstallments;
    }

    public void setCurrentInstallments(int currentInstallments) {
        this.currentInstallments = currentInstallments;
    }

    public boolean isTransactionFixed() {
        return transactionFixed;
    }

    public void setTransactionFixed(boolean transactionFixed) {
        this.transactionFixed = transactionFixed;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
