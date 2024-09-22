package br.com.systec.fintrack.financial.accountPayment.jms;

public class AccountPaymentJmsVO {

    private Long tenantId;
    private AccountPaymentJmsType jmsType;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public AccountPaymentJmsType getJmsType() {
        return jmsType;
    }

    public void setJmsType(AccountPaymentJmsType jmsType) {
        this.jmsType = jmsType;
    }
}
