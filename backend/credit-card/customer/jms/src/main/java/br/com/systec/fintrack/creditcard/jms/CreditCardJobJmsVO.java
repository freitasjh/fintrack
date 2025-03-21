package br.com.systec.fintrack.creditcard.jms;

import java.io.Serial;
import java.io.Serializable;

public class CreditCardJobJmsVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2398221834544225188L;
    private Long tenantId;
    private String closeDay;
    private String dueDay;
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

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getDueDay() {
        return dueDay;
    }
}
