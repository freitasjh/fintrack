package br.com.systec.fintrack.invoice.jms;

import java.io.Serial;
import java.io.Serializable;

public class CreditCardInvoiceJmsVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2266191520533527177L;
    private Long tenantId;
    private Long creditCardId;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }
}
