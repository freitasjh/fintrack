package br.com.systec.fintrack.creditcard.jms;

import java.io.Serializable;

public class CreditCardJobJmsVO implements Serializable {

    private Long tenantId;
    private String closeDay;
    private Long creditCardId;
    private String creditCardJobType;

    public CreditCardJobJmsVO() {
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getCloseDay() {
        return closeDay;
    }

    public void setCloseDay(String closeDay) {
        this.closeDay = closeDay;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardJobType() {
        return creditCardJobType;
    }

    public void setCreditCardJobType(String creditCardJobType) {
        this.creditCardJobType = creditCardJobType;
    }
}
