package br.com.systec.fintrack.financial.accountReceivable.model;

import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.transaction.model.Transaction;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class AccountReceivable extends Transaction {

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.INCOMING;
    }
}
