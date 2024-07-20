package br.com.systec.controle.financeiro.financial.accountPayment.model;

import br.com.systec.controle.financeiro.financial.transaction.model.Transaction;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class AccountPayment extends Transaction {
}
