package br.com.systec.fintrack.financial.accountPayment.api.v1.mapper;

import br.com.systec.fintrack.financial.accountPayment.api.v1.dto.AccountPaymentDTO;
import br.com.systec.fintrack.financial.accountPayment.api.v1.dto.AccountPaymentInputDTO;
import br.com.systec.fintrack.financial.accountPayment.fake.AccountPaymentFake;
import br.com.systec.fintrack.financial.accountPayment.model.AccountPayment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class AccountPaymentMapperTest {

    @Test
    void whenConvertAccountPaymentToAccountPaymentDTO() {
        AccountPayment accountPayment = AccountPaymentFake.toFake();

        AccountPaymentDTO paymentDTOConverted = AccountPaymentMapper.toDTO(accountPayment);

        Assertions.assertThat(paymentDTOConverted).isNotNull();
        Assertions.assertThat(paymentDTOConverted.getId()).isEqualTo(accountPayment.getId());
        Assertions.assertThat(paymentDTOConverted.getDescription()).isEqualTo(accountPayment.getDescription());
        Assertions.assertThat(paymentDTOConverted.getDateRegister()).isEqualTo(accountPayment.getDateRegister());
        Assertions.assertThat(paymentDTOConverted.getDateProcessed()).isEqualTo(accountPayment.getDateProcessed());
        Assertions.assertThat(paymentDTOConverted.getBankAccount()).isEqualTo(accountPayment.getBankAccount().getDescription());
        Assertions.assertThat(paymentDTOConverted.getCategory()).isEqualTo(accountPayment.getCategory().getDescription());
        Assertions.assertThat(paymentDTOConverted.getAmount()).isEqualTo(accountPayment.getAmount());
        Assertions.assertThat(paymentDTOConverted.getPaymentDueDate()).isEqualTo(accountPayment.getPaymentDueDate());
    }

    @Test
    void whenConvertAccountPaymentInputDTOToAccountPayment() {
        AccountPayment accountPayment = AccountPaymentFake.toFake();

        AccountPaymentInputDTO accountPaymentConverted = AccountPaymentMapper.toInputDTO(accountPayment);

        Assertions.assertThat(accountPaymentConverted).isNotNull();
        Assertions.assertThat(accountPaymentConverted.getId()).isEqualTo(accountPayment.getId());
        Assertions.assertThat(accountPaymentConverted.getDescription()).isEqualTo(accountPayment.getDescription());
        Assertions.assertThat(accountPaymentConverted.getCategoryId()).isEqualTo(accountPayment.getCategory().getId());
        Assertions.assertThat(accountPaymentConverted.getBankAccountId()).isEqualTo(accountPayment.getBankAccount().getId());
        Assertions.assertThat(accountPaymentConverted.getAmount()).isEqualTo(accountPayment.getAmount());
        Assertions.assertThat(accountPaymentConverted.getDateProcessed()).isEqualTo(accountPayment.getDateProcessed());
        Assertions.assertThat(accountPaymentConverted.getDateRegister()).isEqualTo(accountPayment.getDateRegister());
        Assertions.assertThat(accountPaymentConverted.isProcessed()).isEqualTo(accountPayment.isProcessed());
        Assertions.assertThat(accountPaymentConverted.getPaymentDueDate()).isEqualTo(accountPayment.getPaymentDueDate());
    }

    @Test
    void whenConvertListAccountPaymentToListAccountPaymentDTO() {
        List<AccountPayment> listOfAccountPayment = List.of(AccountPaymentFake.toFake());

        List<AccountPaymentDTO> listOfAccountPaymentConverted = AccountPaymentMapper.toListDTO(listOfAccountPayment);

        Assertions.assertThat(listOfAccountPaymentConverted).isNotNull();
        Assertions.assertThat(listOfAccountPaymentConverted).isNotEmpty();
        Assertions.assertThat(listOfAccountPaymentConverted.size()).isEqualTo(listOfAccountPayment.size());
    }

    @Test
    void whenPageAccountPaymentToPageAccountPaymentDTO() {
        Page<AccountPayment> pageOfAccountPayment = new PageImpl<>(List.of(AccountPaymentFake.toFake()));

        Page<AccountPaymentDTO> pageOfAccountPaymentConverted = AccountPaymentMapper.toPageDTO(pageOfAccountPayment);

        Assertions.assertThat(pageOfAccountPaymentConverted).isNotNull();
        Assertions.assertThat(pageOfAccountPaymentConverted.getTotalPages()).isEqualTo(pageOfAccountPayment.getTotalPages());
        Assertions.assertThat(pageOfAccountPaymentConverted.getTotalElements()).isEqualTo(pageOfAccountPayment.getTotalElements());
        Assertions.assertThat(pageOfAccountPaymentConverted.getContent().get(0).getId()).isEqualTo(pageOfAccountPayment.getContent().get(0).getId());
    }
}
