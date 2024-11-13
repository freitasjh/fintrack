package br.com.systec.fintrack.invoice.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCardFutureInvoiceVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1176624787203002890L;
    private final LocalDate dueDate;
    private final String creditCardName;
    private final double amount;

    public CreditCardFutureInvoiceVO(LocalDate dueDate, String creditCardName, double amount) {
        this.dueDate = dueDate;
        this.creditCardName = creditCardName;
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public double getAmount() {
        return amount;
    }
}
