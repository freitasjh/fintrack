package br.com.systec.fintrack.creditcard.impl.fake;


import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankAccount.model.AccountType;
import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.creditcard.model.BrandType;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.model.CreditCardStatusType;


public class CreditCardFake {

    public static CreditCard toFake() {
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
