package br.com.systec.fintrack.transaction.fake;

import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankaccount.model.AccountType;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.creditcard.model.BrandType;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.model.CreditCardStatusType;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;

public class CreditCardTransactionFake {

    public static CreditCardTransaction toFake() {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setId(1L);
        creditCardTransaction.setAmount(1250.10);
        creditCardTransaction.setDescription("Teste de cadastro");
        creditCardTransaction.setInstallments(1);
        creditCardTransaction.setCreditCard(toFakeCreditCard());

        return creditCardTransaction;
    }

//    public static CreditCardTransactionInputDTO toFakeInputDTO() {
//        CreditCardTransactionInputDTO transaction = new CreditCardTransactionInputDTO();
//        transaction.setId(1L);
//        transaction.setDescription("teste de transação");
//        transaction.setInstallments(1);
//        transaction.setAmount(100.0);
//        transaction.setCreditCardId(1L);
//
//        return transaction;
//    }
//
//    public static CreditCardTransactionDTO toFakeDTO() {
//        CreditCardTransactionDTO dto = new CreditCardTransactionDTO();
//        dto.setId(1L);
//        dto.setInstallments(1);
//        dto.setAmount(100.0);
//        dto.setCreditCardName(toFakeCreditCard().getName());
//        dto.setDateCreate(LocalDate.now());
//
//        return dto;
//    }

    public static CreditCard toFakeCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setName("Cartao itau black");
        creditCard.setNameCreditCard("Joao h Freitas");
        creditCard.setNumber("1234567890");
        creditCard.setCvc("123");
        creditCard.setDueDay("15");
        creditCard.setClosingDate("09");
        creditCard.setInterestRate(0.2);
        creditCard.setStatus(CreditCardStatusType.ACTIVE);
        creditCard.setBankAccount(fakeBankAccount());
        creditCard.setBrand(BrandType.VISA);
        creditCard.setTotalLimit(10000);
        creditCard.setAvailableLimit(10000);

        return creditCard;
    }

    private static BankAccount fakeBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBank(new Bank(1L));
        bankAccount.setDescription("Teste de banco");
        bankAccount.setAgency("387");
        bankAccount.setAccount("123456");
        bankAccount.setAccountType(AccountType.CURRENT_ACCOUNT);
        bankAccount.setTenantId(1L);

        return bankAccount;
    }

}
