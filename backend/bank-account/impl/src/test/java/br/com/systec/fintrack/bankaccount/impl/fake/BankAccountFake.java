package br.com.systec.fintrack.bankaccount.impl.fake;

import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankAccount.model.AccountType;
import br.com.systec.fintrack.bankAccount.model.BankAccount;

public class BankAccountFake {

    public static BankAccount fake() {
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
