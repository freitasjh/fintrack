package br.com.systec.fintrack.financial.creditCard.fake;

import br.com.systec.fintrack.creditCard.fake.CreditCardFake;
import br.com.systec.fintrack.financial.creditCard.transaction.api.v1.dto.CreditCardTransactionDTO;
import br.com.systec.fintrack.financial.creditCard.transaction.api.v1.dto.CreditCardTransactionInputDTO;
import br.com.systec.fintrack.financial.creditCard.transaction.model.CreditCardTransaction;

import java.time.LocalDate;

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

    public static CreditCardTransactionInputDTO toFakeInputDTO() {
        CreditCardTransactionInputDTO transaction = new CreditCardTransactionInputDTO();
        transaction.setId(1L);
        transaction.setDescription("teste de transação");
        transaction.setInstallments(1);
        transaction.setAmount(100.0);
        transaction.setCreditCardId(1L);

        return transaction;
    }

    public static CreditCardTransactionDTO toFakeDTO() {
        CreditCardTransactionDTO dto = new CreditCardTransactionDTO();
        dto.setId(1L);
        dto.setInstallments(1);
        dto.setAmount(100.0);
        dto.setCreditCardName(CreditCardFake.toFake().getName());
        dto.setDateCreate(LocalDate.now());

        return dto;
    }

}
