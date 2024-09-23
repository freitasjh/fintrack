package br.com.systec.fintrack.creditCard.jms;

import br.com.systec.fintrack.creditCard.model.CreditCard;

public class CreditCardJmsVO {

    private Long tenantId;
    private CreditCard creditCard;
    private String creditCardJobType;

    public Long getTenantId() {
        return tenantId;
    }

}
