package br.com.systec.fintrack.fake;

import br.com.systec.fintrack.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.fintrack.administrator.bankAccount.enums.AccountType;
import br.com.systec.fintrack.administrator.bankAccount.model.BankAccount;

public class BankAccountFake {

    public static BankAccount fake() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBank(BankFake.toFake());
        bankAccount.setDescription("Teste de banco");
        bankAccount.setAgency("387");
        bankAccount.setAccount("123456");
        bankAccount.setAccountType(AccountType.CURRENT_ACCOUNT);
        bankAccount.setTenantId(1L);

        return bankAccount;
    }


    public static BankAccountInputDTO fakeInputDTO() {
        BankAccountInputDTO bankAccount = new BankAccountInputDTO();
        bankAccount.setId(1L);
        bankAccount.setBankId(BankFake.toFake().getId());
        bankAccount.setDescription("Teste de banco");
        bankAccount.setAgency("387");
        bankAccount.setAccount("123456");
        bankAccount.setAccountType(AccountType.CURRENT_ACCOUNT.getCode());


        return bankAccount;
    }
}
