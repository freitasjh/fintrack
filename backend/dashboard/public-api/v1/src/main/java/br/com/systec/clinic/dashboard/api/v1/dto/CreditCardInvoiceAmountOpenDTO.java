package br.com.systec.clinic.dashboard.api.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class CreditCardInvoiceAmountOpenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8213585372669704248L;
    private final Double amount;

    public CreditCardInvoiceAmountOpenDTO(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }
}
