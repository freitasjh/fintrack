package br.com.systec.fintrack.financial.accountReceivable.api.v1.converter;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.financial.accountReceivable.api.v1.dto.AccountReceivableDTO;
import br.com.systec.fintrack.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public class AccountReceivableConverter {

    private AccountReceivableConverter(){}

    public static AccountReceivableDTO toDTO(AccountReceivable accountReceivable) {
        AccountReceivableDTO accountReceivableDTO = new AccountReceivableDTO();
        accountReceivableDTO.setId(accountReceivable.getId());
        accountReceivableDTO.setAmount(accountReceivable.getAmount());
        accountReceivableDTO.setDateProcessed(accountReceivable.getDateProcessed());
        accountReceivableDTO.setDateRegister(accountReceivable.getDateRegister());
        accountReceivableDTO.setProcessed(accountReceivableDTO.isProcessed());
        accountReceivableDTO.setBankAccountDescription(accountReceivable.getBankAccount().getDescription());

        return accountReceivableDTO;
    }

    public static List<AccountReceivableDTO> toListDTO(List<AccountReceivable> listOfAccountReceivable) {
        return listOfAccountReceivable.stream().map(AccountReceivableConverter::toDTO).toList();
    }

    public static Page<AccountReceivableDTO> toPageDTO(Page<AccountReceivable> pageOfAccountReceivable) {
        return pageOfAccountReceivable.map(AccountReceivableConverter::toDTO);
    }

    public static AccountReceivableInputDTO toInputDTO(AccountReceivable accountReceivable) {
        AccountReceivableInputDTO inputDTO = new AccountReceivableInputDTO();
        inputDTO.setId(accountReceivable.getId());
        inputDTO.setDescription(accountReceivable.getDescription());
        inputDTO.setAmount(accountReceivable.getAmount());
        inputDTO.setBankAccountId(accountReceivable.getBankAccount().getId());
        inputDTO.setDateRegister(accountReceivable.getDateRegister());
        inputDTO.setDateProcessed(accountReceivable.getDateProcessed());

        return inputDTO;
    }

    public static AccountReceivable toModel(AccountReceivableInputDTO inputDTO){
        AccountReceivable accountReceivable = new AccountReceivable();
        accountReceivable.setId(inputDTO.getId());
        accountReceivable.setDateProcessed(inputDTO.getDateProcessed());
        accountReceivable.setDateRegister(inputDTO.getDateRegister());
        accountReceivable.setAmount(inputDTO.getAmount());
        accountReceivable.setProcessed(inputDTO.isProcessed());
        accountReceivable.setDescription(inputDTO.getDescription());
        accountReceivable.setBankAccount(new BankAccount(inputDTO.getBankAccountId()));
        accountReceivable.setCategoryTransactionType(CategoryTransactionType.RECEIVER);
        if(inputDTO.getDateRegister() == null){
            accountReceivable.setDateRegister(new Date());
        }

        return accountReceivable;
    }
}
