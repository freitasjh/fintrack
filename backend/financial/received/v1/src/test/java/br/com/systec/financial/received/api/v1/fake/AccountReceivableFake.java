package br.com.systec.financial.received.api.v1.fake;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableDTO;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;

import java.util.Date;

public class AccountReceivableFake {

    public static AccountReceivable toFake() {
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setId(1L);
        accountReceivable.setBankAccount(new BankAccount(1L));
        accountReceivable.getBankAccount().setDescription("Teste de banco");
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
        accountReceivable.setBankAccountId(1L);
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);

        return accountReceivable;
    }

    public static AccountReceivableDTO toDTO() {
        AccountReceivableDTO accountReceivable = new AccountReceivableDTO();
        accountReceivable.setBankAccountDescription("Banco teste");
        accountReceivable.setDescription("Cadastro de nova conta");
        accountReceivable.setAmount(1000.0);
        accountReceivable.setDateRegister(new Date());
        accountReceivable.setDateProcessed(new Date());
        accountReceivable.setProcessed(true);

        return accountReceivable;
    }
}
