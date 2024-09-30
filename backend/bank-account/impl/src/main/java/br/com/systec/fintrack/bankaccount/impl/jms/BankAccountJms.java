package br.com.systec.fintrack.bankaccount.impl.jms;

import br.com.systec.fintrack.commons.model.TransactionType;

public class BankAccountJms {
    private Long tenantId;
    private Long bankAccountId;
    private Double amountBalance;
    private TransactionType transactionType;

    public BankAccountJms() {
    }

    public BankAccountJms(Long tenantId, Long bankAccountId, Double amountBalance, TransactionType transactionType) {
        this.tenantId = tenantId;
        this.bankAccountId = bankAccountId;
        this.amountBalance = amountBalance;
        this.transactionType = transactionType;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Double getAmountBalance() {
        return amountBalance;
    }

    public void setAmountBalance(Double amountBalance) {
        this.amountBalance = amountBalance;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "BankAccountJms{" +
                "tenantId=" + tenantId +
                ", bankAccountId=" + bankAccountId +
                ", amountBalance=" + amountBalance +
                ", transactionType=" + transactionType +
                '}';
    }
}
