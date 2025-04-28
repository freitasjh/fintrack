package br.com.systec.fintrack.financial.received.impl.fake;

import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankaccount.model.AccountType;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;

import java.util.Date;

public class AccountReceivableFake {

    public static AccountReceivable toFake() {
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setBankAccount(bankAccountFake());
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setTenantId(1L);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);

        return accountReceivable;
    }

    public static AccountReceivableVO toFakeVO() {
        AccountReceivableVO accountReceivable = new AccountReceivableVO();
        accountReceivable.setBankAccount(bankAccountFake());
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setTenantId(1L);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);
        accountReceivable.setTransactionType(TransactionType.INCOMING);

        return accountReceivable;
    }

    private static BankAccount bankAccountFake() {
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
