package br.com.systec.fintrack.financial.accountReceivable.fake;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.fake.BankAccountFake;
import br.com.systec.fintrack.financial.accountReceivable.api.v1.dto.AccountReceivableDTO;
import br.com.systec.fintrack.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.accountReceivable.model.AccountReceivable;

import java.util.Date;

public class AccountReceivableFake {

    public static AccountReceivable toFake() {
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setBankAccount(BankAccountFake.fake());
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setTenantId(1L);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);

        return accountReceivable;
    }

    public static AccountReceivableInputDTO toInputDTO() {
        AccountReceivableInputDTO accountReceivable = new AccountReceivableInputDTO();
        accountReceivable.setBankAccountId(BankAccountFake.fake().getId());
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);

        return accountReceivable;
    }

    public static AccountReceivableDTO toDTO() {
        BankAccount bankAccount = BankAccountFake.fake();
        AccountReceivableDTO accountReceivable = new AccountReceivableDTO();
        accountReceivable.setBankAccountDescription(bankAccount.getDescription());
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);

        return accountReceivable;
    }
}
