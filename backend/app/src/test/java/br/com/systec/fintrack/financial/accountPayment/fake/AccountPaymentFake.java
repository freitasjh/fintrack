package br.com.systec.fintrack.financial.accountPayment.fake;

import br.com.systec.fintrack.fake.BankAccountFake;
import br.com.systec.fintrack.fake.CategoryFake;
import br.com.systec.fintrack.financial.accountPayment.api.v1.dto.AccountPaymentInputDTO;
import br.com.systec.fintrack.financial.accountPayment.model.AccountPayment;
import br.com.systec.fintrack.financial.transaction.enums.CategoryTransactionType;
import br.com.systec.fintrack.financial.transaction.enums.TransactionType;

import java.util.Date;

public class AccountPaymentFake {

    public static AccountPayment toFake() {
        AccountPayment accountPayment = new AccountPayment();
        accountPayment.setId(1L);
        accountPayment.setDescription("Teste de pagamento");
        accountPayment.setBankAccount(BankAccountFake.fake());
        accountPayment.setCategory(CategoryFake.fakeCategory());
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
        accountPayment.setBankAccountId(BankAccountFake.fake().getId());
        accountPayment.setCategoryId(CategoryFake.fakeCategory().getId());
        accountPayment.setDateRegister(new Date());
        accountPayment.setDateProcessed(new Date());
        accountPayment.setProcessed(true);
        accountPayment.setAmount(150.0);
        accountPayment.setPaymentDueDate(new Date());

        return accountPayment;
    }
}
