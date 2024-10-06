package br.com.systec.fintrack.financial.received.impl.jms;

public class BankAccountJmsVO {

    private Long tenantId;
    private Long bankAccountId;
    private double initialValue;

    public BankAccountJmsVO() {
    }

    public BankAccountJmsVO(Long tenantId, Long bankAccountId, double initialValue) {
        this.tenantId = tenantId;
        this.bankAccountId = bankAccountId;
        this.initialValue = initialValue;
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

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    @Override
    public String toString() {
        return "BankAccountJmsVO{" +
                "tenantId=" + tenantId +
                ", bankAccountId=" + bankAccountId +
                ", initialValue=" + initialValue +
                '}';
    }
}
