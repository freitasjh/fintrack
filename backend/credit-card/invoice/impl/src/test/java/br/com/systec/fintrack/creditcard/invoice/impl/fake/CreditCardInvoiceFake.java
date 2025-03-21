package br.com.systec.fintrack.creditcard.invoice.impl.fake;

import br.com.systec.fintrack.invoice.model.CreditCardInvoice;

public class CreditCardInvoiceFake {

    public static CreditCardInvoice fake() {
        CreditCardInvoice creditCardInvoice = new CreditCardInvoice();
        creditCardInvoice.setId(1L);

        return creditCardInvoice;

    }
}
