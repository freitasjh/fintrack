package br.com.systec.fintrack.creditCard.api.v1.mapper;

import br.com.systec.fintrack.administrator.bankAccount.model.BankAccount;
import br.com.systec.fintrack.creditCard.api.v1.dto.CreditCardDTO;
import br.com.systec.fintrack.creditCard.api.v1.dto.CreditCardInputDTO;
import br.com.systec.fintrack.creditCard.model.BrandType;
import br.com.systec.fintrack.creditCard.model.CreditCard;
import org.springframework.data.domain.Page;

public class CreditCardMapper {

    public static CreditCard toEntity(CreditCardInputDTO inputDTO) {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(inputDTO.getId());
        creditCard.setName(inputDTO.getName());
        creditCard.setNameCreditCard(inputDTO.getNameCreditCard());
        creditCard.setBrand(inputDTO.getBrand());
        creditCard.setNumber(inputDTO.getNumber());
        creditCard.setCvc(inputDTO.getCvc());
        creditCard.setAvailableLimit(inputDTO.getAvailableLimit());
        creditCard.setTotalLimit(inputDTO.getTotalLimit());
        creditCard.setInterestRate(inputDTO.getInterestRate());
        creditCard.setDueDay(inputDTO.getDueDay());
        creditCard.setClosingDate(inputDTO.getClosingDate());
        creditCard.setBankAccount(new BankAccount(inputDTO.getBankAccountId()));
        creditCard.setStatus(inputDTO.getStatus());

        if(creditCard.getBrand() == null){
            creditCard.setBrand(BrandType.VISA);
        }

        return creditCard;
    }

    public static CreditCardInputDTO toInputDTO(CreditCard creditCard) {
        CreditCardInputDTO inputDTO = new CreditCardInputDTO();
        inputDTO.setId(creditCard.getId());
        inputDTO.setBankAccountId(creditCard.getBankAccount().getId());
        inputDTO.setName(creditCard.getName());
        inputDTO.setNameCreditCard(creditCard.getNameCreditCard());
        inputDTO.setDueDay(creditCard.getDueDay());
        inputDTO.setStatus(creditCard.getStatus());
        inputDTO.setNumber(creditCard.getNumber());
        inputDTO.setCvc(creditCard.getCvc());
        inputDTO.setInterestRate(creditCard.getInterestRate());
        inputDTO.setTotalLimit(creditCard.getTotalLimit());
        inputDTO.setAvailableLimit(creditCard.getAvailableLimit());
        inputDTO.setClosingDate(creditCard.getClosingDate());
        inputDTO.setBrand(creditCard.getBrand());

        if(creditCard.getBankAccount() != null) {
            inputDTO.setBankAccountId(creditCard.getBankAccount().getId());
        }

        return inputDTO;
    }

    public static CreditCardDTO toDTO(CreditCard creditCard) {
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setId(creditCard.getId());
        creditCardDTO.setName(creditCard.getName());
        creditCardDTO.setTotalLimit(creditCard.getTotalLimit());
        creditCardDTO.setAvailableLimit(creditCard.getAvailableLimit());
        creditCardDTO.setTotalLimit(creditCard.getTotalLimit());

        if(creditCard.getBankAccount() != null) {
            creditCardDTO.setBankAccountName(creditCard.getBankAccount().getDescription());
        }

        return creditCardDTO;
    }

    public static Page<CreditCardDTO> toPageDTO(Page<CreditCard> pageOfCreditCard) {
        return pageOfCreditCard.map(CreditCardMapper::toDTO);
    }
}
