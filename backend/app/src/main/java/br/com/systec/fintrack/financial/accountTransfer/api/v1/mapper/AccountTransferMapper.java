package br.com.systec.fintrack.financial.accountTransfer.api.v1.mapper;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.accountTransfer.api.v1.dto.AccountTransferInputDTO;
import br.com.systec.fintrack.financial.accountTransfer.api.v1.dto.AccountTransferResponseDTO;
import br.com.systec.fintrack.financial.accountTransfer.model.AccountTransfer;
import org.springframework.data.domain.Page;

import java.util.List;

public class AccountTransferMapper {

    private AccountTransferMapper(){}

    public static AccountTransfer toEntity(AccountTransferInputDTO inputDTO) {
        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setId(inputDTO.getId());
        accountTransfer.setBankAccountFrom(new BankAccount(inputDTO.getBankAccountFromId()));
        accountTransfer.setBankAccountTo(new BankAccount(inputDTO.getBankAccountToId()));
        accountTransfer.setDescription(inputDTO.getDescription());
        accountTransfer.setAmount(inputDTO.getAmount());
        accountTransfer.setTransferDate(inputDTO.getTransferDate());

        if(inputDTO.getId() == null){
            accountTransfer.setTenantId(TenantContext.getTenant());
        }

        return accountTransfer;
    }

    public static AccountTransferInputDTO toInputDTO(AccountTransfer accountTransfer) {
        AccountTransferInputDTO inputDTO = new AccountTransferInputDTO();
        inputDTO.setId(accountTransfer.getId());
        inputDTO.setDescription(accountTransfer.getDescription());
        inputDTO.setTransferDate(accountTransfer.getTransferDate());
        inputDTO.setBankAccountToId(accountTransfer.getBankAccountTo().getId());
        inputDTO.setBankAccountFromId(accountTransfer.getBankAccountFrom().getId());

        return inputDTO;
    }

    public static AccountTransferResponseDTO toResponseDTO(AccountTransfer accountTransfer) {
        AccountTransferResponseDTO responseDTO = new AccountTransferResponseDTO();
        responseDTO.setId(accountTransfer.getId());
        responseDTO.setDescription(accountTransfer.getDescription());
        responseDTO.setBankToDescription(accountTransfer.getBankAccountTo().getDescription());
        responseDTO.setBankFromDescription(accountTransfer.getBankAccountFrom().getDescription());
        responseDTO.setAmount(accountTransfer.getAmount());
        responseDTO.setTransferDate(accountTransfer.getTransferDate());

        return responseDTO;
    }

    public static List<AccountTransferResponseDTO> toListResponseDTO(List<AccountTransfer> listOfAccountTransfer) {
        return listOfAccountTransfer.stream().map(AccountTransferMapper::toResponseDTO).toList();
    }

    public static Page<AccountTransferResponseDTO> toPageResponseDTO(Page<AccountTransfer> pageOfAccountTransfer) {
        return pageOfAccountTransfer.map(AccountTransferMapper::toResponseDTO);
    }
}
