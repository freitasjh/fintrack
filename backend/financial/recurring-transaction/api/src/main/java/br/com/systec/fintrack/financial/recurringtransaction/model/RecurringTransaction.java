package br.com.systec.fintrack.financial.recurringtransaction.model;

import br.com.systec.fintrack.commons.model.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recurring_transaction")
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "frequency_type")
    @Enumerated(EnumType.STRING)
    private FrequencyType frequencyType;
    @Column(name = "total_instalments")
    private int totalInstallments;
    @Column(name = "current_installments")
    private int currentInstallments;
    @Column(name = "transaction_fixed")
    private boolean transactionFixed;
    @Column(name = "tenant_id")
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isTransactionFixed() {
        return transactionFixed;
    }

    public void setTransactionFixed(boolean transactionFixed) {
        this.transactionFixed = transactionFixed;
    }
}
