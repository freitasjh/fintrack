package br.com.systec.fintrack.financial.transfer.api.v1.fake;

import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankaccount.model.AccountType;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;
import br.com.systec.fintrack.financial.transfer.v1.dto.AccountTransferInputDTO;
import br.com.systec.fintrack.financial.transfer.v1.dto.AccountTransferResponseDTO;

import java.util.Date;

public class AccountTransferFake {

    public static AccountTransfer toFake() {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setId(1L);
        accountTransfer.setDescription("Teste de transferencia");
        accountTransfer.setTransferDate(new Date());

        BankAccount bankAccountTo =fakeBankAccount();
        bankAccountTo.setBalance(1000.0);
        BankAccount bankAccountFrom = fakeBankAccount();
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
