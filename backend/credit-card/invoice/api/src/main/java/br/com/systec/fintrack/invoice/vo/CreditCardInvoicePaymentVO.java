package br.com.systec.fintrack.invoice.vo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class CreditCardInvoicePaymentVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1272874513845528804L;

    private Long creditCardId;
    private Long creditCardInvoiceId;
    private Long bankAccountId;
    private double amount;
    private LocalDate datePay;

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public Long getCreditCardInvoiceId() {
        return creditCardInvoiceId;
    }

    public void setCreditCardInvoiceId(Long creditCardInvoiceId) {
        this.creditCardInvoiceId = creditCardInvoiceId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDatePay() {
        return datePay;
    }

    public void setDatePay(LocalDate datePay) {
        this.datePay = datePay;
    }
}
