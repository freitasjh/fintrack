package br.com.systec.fintrack.financial.transfer.impl.fake;

import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankAccount.model.AccountType;
import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;

import java.util.Date;

public class AccountTransferFake {

    public static AccountTransfer toFake() {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setId(1L);
        accountTransfer.setDescription("Teste de transferencia");
        accountTransfer.setTransferDate(new Date());

        BankAccount bankAccountTo = fakeBankAccount();
        bankAccountTo.setBalance(1000.0);
        BankAccount bankAccountFrom = fakeBankAccount();
        bankAccountFrom.setId(2L);
        bankAccountFrom.setDescription("Banco teste 2");
        bankAccountFrom.setBalance(100.00);

        accountTransfer.setBankAccountTo(bankAccountTo);
        accountTransfer.setBankAccountFrom(bankAccountFrom);

        return accountTransfer;
    }

    public static BankAccount fakeBankAccount() {
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
