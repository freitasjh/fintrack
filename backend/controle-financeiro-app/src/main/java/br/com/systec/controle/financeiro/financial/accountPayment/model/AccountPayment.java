package br.com.systec.controle.financeiro.financial.accountPayment.model;

import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import br.com.systec.controle.financeiro.financial.transaction.model.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@DiscriminatorValue("1")
public class AccountPayment extends Transaction {

    @Column(name = "payment_due_date")
    private Date paymentDueDate;

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.EXPENSE;
    }
}
