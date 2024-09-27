package br.com.systec.fintrack.administrator.bank.api.v1.converter;

import br.com.systec.fintrack.administrator.bank.api.v1.dto.BankDTO;
import br.com.systec.fintrack.bank.model.Bank;
import org.springframework.data.domain.Page;

public class BankConverter {
    private static BankConverter instance;

    private BankConverter(){}

    public static BankConverter getInstance(){
        if (instance == null) {
            instance = new BankConverter();
        }

        return instance;
    }

    public BankDTO toDTO(final Bank bank) {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setId(bank.getId());
        bankDTO.setCode(bank.getCode());
        bankDTO.setName(bank.getName());
        bankDTO.setWebsite(bank.getWebsite());

        return bankDTO;
    }

    public Page<BankDTO> toPageDTO(Page<Bank> listPageBank){
        return listPageBank.map(this::toDTO);
    }

}
