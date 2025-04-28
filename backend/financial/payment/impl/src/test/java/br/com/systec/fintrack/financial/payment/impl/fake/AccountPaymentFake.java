package br.com.systec.fintrack.financial.payment.impl.fake;

import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankaccount.model.AccountType;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;

import java.util.Date;

public class AccountPaymentFake {

    public static AccountPayment toFake() {
        AccountPayment accountPayment = new AccountPayment();
        accountPayment.setId(1L);
        accountPayment.setDescription("Teste de pagamento");
        accountPayment.setCategory(new Category(1L));
        accountPayment.setDateRegister(new Date());
        accountPayment.setDateProcessed(new Date());
        accountPayment.setProcessed(true);
        accountPayment.setBankAccount(bankAccount());
        accountPayment.setTransactionType(TransactionType.EXPENSE);
        accountPayment.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        accountPayment.setAmount(1000.0);
        accountPayment.setPaymentDueDate(new Date());

        return accountPayment;
    }

    public static BankAccount bankAccount() {
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
