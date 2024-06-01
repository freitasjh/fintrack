package br.com.systec.controle.financeiro.accountReceivable.fake;

import br.com.systec.controle.financeiro.fake.CategoryFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;

import java.time.LocalDateTime;

public class AccountReceivableFake {

    public static AccountReceivable fake() {
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setDescription("Salario");
        accountReceivable.setAccountId(1L);
        accountReceivable.setAmount(50000);
        accountReceivable.setDateReceiver(LocalDateTime.now());
        accountReceivable.setDateRegister(LocalDateTime.now());

        return accountReceivable;
    }

    public static AccountReceivableInputDTO fakeInputDTO() {
        AccountReceivableInputDTO accountReceivable = new AccountReceivableInputDTO();
        accountReceivable.setDescription("Salario");
        accountReceivable.setAccountId(1L);
        accountReceivable.setAmount(50000);
        accountReceivable.setDateReceiver(LocalDateTime.now());
        accountReceivable.setDateRegister(LocalDateTime.now());

        return accountReceivable;
    }
}
