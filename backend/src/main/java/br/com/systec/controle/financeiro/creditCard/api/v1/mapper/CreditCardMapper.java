package br.com.systec.controle.financeiro.creditCard.api.v1.mapper;

import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.creditCard.api.v1.dto.CreditCardInputDTO;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;

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
}
