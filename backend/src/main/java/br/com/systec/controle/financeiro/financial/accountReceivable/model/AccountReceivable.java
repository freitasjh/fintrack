package br.com.systec.controle.financeiro.financial.accountReceivable.model;

import br.com.systec.controle.financeiro.financial.transaction.enums.TransactionType;
import br.com.systec.controle.financeiro.financial.transaction.model.Transaction;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("0")
public class AccountReceivable extends Transaction {

}
