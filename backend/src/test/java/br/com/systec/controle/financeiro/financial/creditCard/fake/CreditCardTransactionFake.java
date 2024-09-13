package br.com.systec.controle.financeiro.financial.creditCard.fake;

import br.com.systec.controle.financeiro.creditCard.fake.CreditCardFake;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.model.CreditCardTransaction;

public class CreditCardTransactionFake {

    public static CreditCardTransaction toFake() {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setId(1L);
        creditCardTransaction.setAmount(1250.10);
        creditCardTransaction.setDescription("Teste de cadastro");
        creditCardTransaction.setInstallments(1);
        creditCardTransaction.setCreditCard(CreditCardFake.toFake());

        return creditCardTransaction;
    }
}
