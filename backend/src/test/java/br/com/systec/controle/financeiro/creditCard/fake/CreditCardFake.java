package br.com.systec.controle.financeiro.creditCard.fake;

import br.com.systec.controle.financeiro.creditCard.api.v1.dto.CreditCardInputDTO;
import br.com.systec.controle.financeiro.creditCard.api.v1.mapper.CreditCardMapper;
import br.com.systec.controle.financeiro.creditCard.model.BrandType;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import br.com.systec.controle.financeiro.creditCard.model.CreditCardStatusType;
import br.com.systec.controle.financeiro.fake.BankAccountFake;

public class CreditCardFake {

    public static CreditCard toFake() {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setName("Cartao itau black");
        creditCard.setNameCreditCard("Joao h Freitas");
        creditCard.setNumber("1234567890");
        creditCard.setCvc("123");
        creditCard.setDueDay("15");
        creditCard.setClosingDate("09");
        creditCard.setInterestRate(0.2);
        creditCard.setStatus(CreditCardStatusType.ACTIVE);
        creditCard.setBankAccount(BankAccountFake.fake());
        creditCard.setBrand(BrandType.VISA);

        return creditCard;
    }

    public static CreditCardInputDTO toFakeInputDTO() {
        return CreditCardMapper.toInputDTO(toFake());
    }

}
