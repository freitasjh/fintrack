package br.com.systec.fintrack.creditCard.api.v1.mapper;

import br.com.systec.fintrack.creditCard.api.v1.dto.CreditCardDTO;
import br.com.systec.fintrack.creditCard.api.v1.dto.CreditCardInputDTO;
import br.com.systec.fintrack.creditCard.fake.CreditCardFake;
import br.com.systec.fintrack.creditCard.model.CreditCard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class CreditCardMapperTest {

    @Test
    void whenConverterCreditCardToCreditCardInputDTO() {
        CreditCard creditCard = CreditCardFake.toFake();

        CreditCardInputDTO creditCardConverted = CreditCardMapper.toInputDTO(creditCard);

        Assertions.assertThat(creditCardConverted).isNotNull();
        Assertions.assertThat(creditCardConverted.getId()).isEqualTo(creditCard.getId());
        Assertions.assertThat(creditCardConverted.getName()).isEqualTo(creditCard.getName());
        Assertions.assertThat(creditCardConverted.getNameCreditCard()).isEqualTo(creditCard.getNameCreditCard());
        Assertions.assertThat(creditCardConverted.getNumber()).isEqualTo(creditCard.getNumber());
        Assertions.assertThat(creditCardConverted.getCvc()).isEqualTo(creditCard.getCvc());
        Assertions.assertThat(creditCardConverted.getDueDay()).isEqualTo(creditCard.getDueDay());
        Assertions.assertThat(creditCardConverted.getClosingDate()).isEqualTo(creditCard.getClosingDate());
        Assertions.assertThat(creditCardConverted.getStatus()).isEqualTo(creditCard.getStatus());
        Assertions.assertThat(creditCardConverted.getBrand()).isEqualTo(creditCard.getBrand());
        Assertions.assertThat(creditCardConverted.getBankAccountId()).isEqualTo(creditCard.getBankAccount().getId());
        Assertions.assertThat(creditCardConverted.getInterestRate()).isEqualTo(creditCard.getInterestRate());
    }

    @Test
    void whenConverterCreditCardInputDTOToCreditCard() {
        CreditCardInputDTO inputDTO = CreditCardFake.toFakeInputDTO();

        CreditCard creditCardConverted = CreditCardMapper.toEntity(inputDTO);

        Assertions.assertThat(creditCardConverted).isNotNull();
        Assertions.assertThat(creditCardConverted.getId()).isEqualTo(inputDTO.getId());
        Assertions.assertThat(creditCardConverted.getName()).isEqualTo(inputDTO.getName());
        Assertions.assertThat(creditCardConverted.getNameCreditCard()).isEqualTo(inputDTO.getNameCreditCard());
        Assertions.assertThat(creditCardConverted.getNumber()).isEqualTo(inputDTO.getNumber());
        Assertions.assertThat(creditCardConverted.getCvc()).isEqualTo(inputDTO.getCvc());
        Assertions.assertThat(creditCardConverted.getDueDay()).isEqualTo(inputDTO.getDueDay());
        Assertions.assertThat(creditCardConverted.getClosingDate()).isEqualTo(inputDTO.getClosingDate());
        Assertions.assertThat(creditCardConverted.getStatus()).isEqualTo(inputDTO.getStatus());
        Assertions.assertThat(creditCardConverted.getBrand()).isEqualTo(inputDTO.getBrand());
        Assertions.assertThat(creditCardConverted.getBankAccount().getId()).isEqualTo(inputDTO.getBankAccountId());
        Assertions.assertThat(creditCardConverted.getInterestRate()).isEqualTo(inputDTO.getInterestRate());
    }

    @Test
    void whenConverterCreditCardToCreditCardDTO() {
        CreditCard creditCard = CreditCardFake.toFake();

        CreditCardDTO creditCardConverted = CreditCardMapper.toDTO(creditCard);

        Assertions.assertThat(creditCardConverted).isNotNull();
        Assertions.assertThat(creditCardConverted.getId()).isEqualTo(creditCard.getId());
        Assertions.assertThat(creditCardConverted.getName()).isEqualTo(creditCard.getName());
        Assertions.assertThat(creditCardConverted.getAvailableLimit()).isEqualTo(creditCard.getAvailableLimit());
        Assertions.assertThat(creditCardConverted.getTotalLimit()).isEqualTo(creditCard.getTotalLimit());
        Assertions.assertThat(creditCardConverted.getBankAccountName()).isEqualTo(creditCard.getBankAccount().getDescription());
    }

    @Test
    void whenConverterPageCreditCardToPageCreditCardDTO() {
        Page<CreditCard> pageCreditCard = new PageImpl<>(List.of(CreditCardFake.toFake()));

        Page<CreditCardDTO> pageConverted = CreditCardMapper.toPageDTO(pageCreditCard);

        Assertions.assertThat(pageConverted).isNotNull();
        Assertions.assertThat(pageConverted.getSize()).isEqualTo(pageCreditCard.getSize());
        Assertions.assertThat(pageConverted.getTotalPages()).isEqualTo(pageCreditCard.getTotalPages());
        Assertions.assertThat(pageConverted.getTotalElements()).isEqualTo(pageCreditCard.getTotalElements());
        Assertions.assertThat(pageConverted.isLast()).isEqualTo(pageCreditCard.isLast());
    }
}
