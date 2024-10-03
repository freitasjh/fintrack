package br.com.systec.fintrack.creditcard.transaction.api.v1.mapper;

import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionDTO;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionInputDTO;
import br.com.systec.fintrack.creditcard.transaction.api.v1.fake.CreditCardTransactionFake;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;

public class CreditCardTransactionMapperTest {

   @Test
    void whenConverterCreditCardTransactionToInputDTO() {
        CreditCardTransaction transactionToConverter = CreditCardTransactionFake.toFake();

        CreditCardTransactionInputDTO transactionConverted = CreditCardTransactionMapper.toInputDTO(transactionToConverter);

        Assertions.assertThat(transactionConverted).isNotNull();
        Assertions.assertThat(transactionConverted.getId()).isEqualTo(transactionToConverter.getId());
        Assertions.assertThat(transactionConverted.getAmount()).isEqualTo(transactionToConverter.getAmount());
        Assertions.assertThat(transactionConverted.getDescription()).isEqualTo(transactionToConverter.getDescription());
        Assertions.assertThat(transactionConverted.getInstallments()).isEqualTo(transactionToConverter.getInstallments());
        Assertions.assertThat(transactionConverted.getCreditCardId()).isEqualTo(transactionToConverter.getCreditCard().getId());
    }

    @Test
    void whenConverterInputDTOToCreditCardTransaction() {
        CreditCardTransactionInputDTO transactionToConverter = CreditCardTransactionFake.toFakeInputDTO();

        CreditCardTransaction transactionConverted = CreditCardTransactionMapper.toEntity(transactionToConverter);

        Assertions.assertThat(transactionConverted).isNotNull();
        Assertions.assertThat(transactionConverted.getId()).isEqualTo(transactionToConverter.getId());
        Assertions.assertThat(transactionConverted.getAmount()).isEqualTo(transactionToConverter.getAmount());
        Assertions.assertThat(transactionConverted.getDescription()).isEqualTo(transactionToConverter.getDescription());
        Assertions.assertThat(transactionConverted.getCreditCard().getId()).isEqualTo(transactionToConverter.getCreditCardId());
        Assertions.assertThat(transactionConverted.getInstallments()).isEqualTo(transactionToConverter.getInstallments());
    }

    @Test
    void whenConverterCreditCardTransactionToDTO() {
        CreditCardTransaction transactionToConverter = CreditCardTransactionFake.toFake();

        CreditCardTransactionDTO transactionConverted = CreditCardTransactionMapper.toDTO(transactionToConverter);

        Assertions.assertThat(transactionConverted).isNotNull();
        Assertions.assertThat(transactionConverted.getId()).isEqualTo(transactionToConverter.getId());
        Assertions.assertThat(transactionConverted.getDescription()).isEqualTo(transactionToConverter.getDescription());
        Assertions.assertThat(transactionConverted.getAmount()).isEqualTo(transactionToConverter.getAmount());
        Assertions.assertThat(transactionConverted.getInstallments()).isEqualTo(transactionToConverter.getInstallments());
        Assertions.assertThat(transactionConverted.getCreditCardName()).isEqualTo(transactionToConverter.getCreditCard().getName());
    }

    @Test
    void whenConverterPageCreditCardTransactionToPageDTO() {
        Page<CreditCardTransaction> pageToConverter = new PageImpl<>(Arrays.asList(CreditCardTransactionFake.toFake()));

        Page<CreditCardTransactionDTO> pageConverted = CreditCardTransactionMapper.toPageDTO(pageToConverter);

        Assertions.assertThat(pageConverted).isNotNull();
        Assertions.assertThat(pageConverted.getTotalPages()).isEqualTo(pageToConverter.getTotalPages());
        Assertions.assertThat(pageConverted.getContent().get(0).getId()).isEqualTo(pageToConverter.getContent().get(0).getId());
    }
}
