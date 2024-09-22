package br.com.systec.fintrack.financial.creditCard.fake;

import br.com.systec.fintrack.creditCard.fake.CreditCardFake;
import br.com.systec.fintrack.financial.creditCard.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.financial.creditCard.invoice.model.InvoiceStatusType;

import java.time.LocalDate;

public class CreditCardInvoiceFake {

    public static CreditCardInvoice toFake() {
        CreditCardInvoice creditCardInvoice = new CreditCardInvoice();
        creditCardInvoice.setId(1L);
        creditCardInvoice.setStatusType(InvoiceStatusType.OPEN);
        creditCardInvoice.setTenantId(1L);
        creditCardInvoice.setCreditCard(CreditCardFake.toFake());
        creditCardInvoice.setDueDate(LocalDate.now()
                .withDayOfMonth(Integer.parseInt(creditCardInvoice.getCreditCard().getDueDay())));

        creditCardInvoice.setDateClose(LocalDate.now()
                .withDayOfMonth(Integer.parseInt(creditCardInvoice.getCreditCard().getClosingDate())));
        return creditCardInvoice;
    }
}
