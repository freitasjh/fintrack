package br.com.systec.fintrack.financial.accountTransfer.fake;

import br.com.systec.fintrack.administrator.bankAccount.model.BankAccount;
import br.com.systec.fintrack.fake.BankAccountFake;
import br.com.systec.fintrack.financial.accountTransfer.api.v1.dto.AccountTransferInputDTO;
import br.com.systec.fintrack.financial.accountTransfer.api.v1.dto.AccountTransferResponseDTO;
import br.com.systec.fintrack.financial.accountTransfer.model.AccountTransfer;

import java.util.Date;

public class AccountTransferFake {

    public static AccountTransfer toFake() {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setId(1L);
        accountTransfer.setDescription("Teste de transferencia");
        accountTransfer.setTransferDate(new Date());

        BankAccount bankAccountTo = BankAccountFake.fake();
        bankAccountTo.setBalance(1000.0);
        BankAccount bankAccountFrom = BankAccountFake.fake();
        bankAccountFrom.setId(2L);
        bankAccountFrom.setDescription("Banco teste 2");
        bankAccountFrom.setBalance(100.00);

        accountTransfer.setBankAccountTo(bankAccountTo);
        accountTransfer.setBankAccountFrom(bankAccountFrom);

        return accountTransfer;
    }

    public static AccountTransferInputDTO toInputDTO() {
        AccountTransferInputDTO inputDTO = new AccountTransferInputDTO();
        inputDTO.setId(1L);
        inputDTO.setDescription("Teste de Transferencia");
        inputDTO.setBankAccountFromId(1L);
        inputDTO.setBankAccountToId(2L);
        inputDTO.setAmount(100.0);
        inputDTO.setTransferDate(new Date());
        inputDTO.setTenantId(1L);

        return inputDTO;
    }

    public static AccountTransferResponseDTO toResponseDTO() {
        AccountTransferResponseDTO responseDTO = new AccountTransferResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setDescription("Transferencia teste");
        responseDTO.setBankToDescription("Banco destino");
        responseDTO.setBankFromDescription("Banco retirada");
        responseDTO.setAmount(100.0);
        responseDTO.setTransferDate(new Date());

        return responseDTO;
    }
}
