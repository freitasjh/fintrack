package br.com.systec.fintrack.financial.recurringtransaction.jms;

import java.io.Serial;
import java.io.Serializable;

public class RecurringTransactionJobVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;
    private Long transactionId;
    private Long tenantId;
    private Long recurringTransactionId;
    private String cronJob;
    private int transactionTypeCode;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }


    public String getCronJob() {
        return cronJob;
    }

    public void setCronJob(String cronJob) {
        this.cronJob = cronJob;
    }

    public int getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(int transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public Long getRecurringTransactionId() {
        return recurringTransactionId;
    }

    public void setRecurringTransactionId(Long recurringTransactionId) {
        this.recurringTransactionId = recurringTransactionId;
    }
}
