package br.com.systec.financial.payment.api.v1.fake;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.v1.dto.AccountPaymentInputDTO;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;

import java.util.Date;

public class AccountPaymentFake {

    public static AccountPayment toFake() {
        AccountPayment accountPayment = new AccountPayment();
        accountPayment.setId(1L);
        accountPayment.setDescription("Teste de pagamento");
        accountPayment.setBankAccount(new BankAccount(1L));
        accountPayment.getBankAccount().setDescription("Teste Banco");
        accountPayment.setCategory(new Category(1L));
        accountPayment.setDateRegister(new Date());
        accountPayment.setDateProcessed(new Date());
        accountPayment.setProcessed(true);
        accountPayment.setTransactionType(TransactionType.EXPENSE);
        accountPayment.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        accountPayment.setAmount(1000.0);
        accountPayment.setPaymentDueDate(new Date());

        return accountPayment;
    }

    public static AccountPaymentInputDTO toInputDTO() {
        AccountPaymentInputDTO accountPayment = new AccountPaymentInputDTO();
        accountPayment.setId(1L);
        accountPayment.setDescription("Teste de pagamento");
        accountPayment.setBankAccountId(1L);
        accountPayment.setCategoryId(1L);
        accountPayment.setDateRegister(new Date());
        accountPayment.setDateProcessed(new Date());
        accountPayment.setProcessed(true);
        accountPayment.setAmount(150.0);
        accountPayment.setPaymentDueDate(new Date());

        return accountPayment;
    }
}
