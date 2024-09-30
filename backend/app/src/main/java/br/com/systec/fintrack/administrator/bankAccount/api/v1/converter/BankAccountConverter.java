package br.com.systec.fintrack.administrator.bankAccount.api.v1.converter;

import br.com.systec.fintrack.administrator.bankAccount.api.v1.dto.BankAccountDTO;
import br.com.systec.fintrack.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bankAccount.model.AccountType;
import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.converter.ConverterPageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BankAccountConverter implements ConverterPageable<BankAccountDTO, BankAccount, BankAccountInputDTO> {


    @Override
    public BankAccountDTO convertToDTO(BankAccount model) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(model.getId());
        bankAccountDTO.setDescription(model.getDescription());
        bankAccountDTO.setAccount(model.getAccount());
        bankAccountDTO.setBankDescription(model.getBank().getName());
        bankAccountDTO.setAgency(model.getAgency());
        bankAccountDTO.setBalance(model.getBalance());

        return bankAccountDTO;
    }

    @Override
    public BankAccountInputDTO convertTOInputDTO(BankAccount model) {
        BankAccountInputDTO inputDTO = new BankAccountInputDTO();
        inputDTO.setId(model.getId());
        inputDTO.setDescription(model.getDescription());
        inputDTO.setAccount(model.getAccount());
        inputDTO.setAgency(model.getAgency());
        inputDTO.setBankId(model.getBank().getId());
        inputDTO.setAccountType(model.getAccountType().getCode());
        inputDTO.setBalance(model.getBalance());;

        return inputDTO;
    }

    @Override
    public BankAccount convertToModel(BankAccountInputDTO inputDTO) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(inputDTO.getId());
        bankAccount.setTenantId(TenantContext.getTenant());
        bankAccount.setBank(new Bank(inputDTO.getBankId()));
        bankAccount.setDescription(inputDTO.getDescription());
        bankAccount.setAgency(inputDTO.getAgency());
        bankAccount.setBalance(inputDTO.getBalance());
        bankAccount.setInitialValue(inputDTO.getInitialValue());
        bankAccount.setAccount(inputDTO.getAccount());
        bankAccount.setAccountType(AccountType.valueOfCode(inputDTO.getAccountType()));

        return bankAccount;
    }

    @Override
    public List<BankAccountDTO> convertToListDTO(List<BankAccount> listOfModel) {
        return listOfModel.stream().map(this::convertToDTO).toList();
    }

    @Override
    public Page<BankAccountDTO> convertePage(Page<BankAccount> pageModel) {
        return pageModel.map(this::convertToDTO);
    }
}
