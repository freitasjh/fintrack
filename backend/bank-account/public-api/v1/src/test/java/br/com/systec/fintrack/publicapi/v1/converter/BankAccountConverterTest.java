package br.com.systec.fintrack.publicapi.v1.converter;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.publicapi.v1.dto.BankAccountDTO;
import br.com.systec.fintrack.publicapi.v1.dto.BankAccountInputDTO;
import br.com.systec.fintrack.publicapi.v1.fake.BankAccountFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class BankAccountConverterTest {

    @Spy
    private BankAccountConverter bankAccountConverter;

    @Test
    void whenConverterBankAccountTOBankAccountDTOTest() {
        BankAccount bankAccount = BankAccountFake.fake();

        BankAccountDTO bankAccountDTO = bankAccountConverter.convertToDTO(bankAccount);

        Assertions.assertThat(bankAccountDTO.getId()).isEqualTo(bankAccount.getId());
        Assertions.assertThat(bankAccountDTO.getAccount()).isEqualTo(bankAccount.getAccount());
        Assertions.assertThat(bankAccountDTO.getAgency()).isEqualTo(bankAccount.getAgency());
        Assertions.assertThat(bankAccountDTO.getDescription()).isEqualTo(bankAccount.getDescription());
        Assertions.assertThat(bankAccountDTO.getBankDescription()).isEqualTo(bankAccount.getBank().getName());
    }

    @Test
    void whenConverterBankAccountToBankAccountInputDTO() {
        BankAccount bankAccount = BankAccountFake.fake();

        BankAccountInputDTO bankAccountDTO = bankAccountConverter.convertTOInputDTO(bankAccount);

        Assertions.assertThat(bankAccountDTO.getId()).isEqualTo(bankAccount.getId());
        Assertions.assertThat(bankAccountDTO.getAccount()).isEqualTo(bankAccount.getAccount());
        Assertions.assertThat(bankAccountDTO.getAgency()).isEqualTo(bankAccount.getAgency());
        Assertions.assertThat(bankAccountDTO.getDescription()).isEqualTo(bankAccount.getDescription());
        Assertions.assertThat(bankAccountDTO.getBankId()).isEqualTo(bankAccount.getBank().getId());
        Assertions.assertThat(bankAccountDTO.getAccountType()).isEqualTo(bankAccount.getAccountType().getCode());
    }

    @Test
    void whenConverterListOfBankAccountToListOfBankAccountDTOTest() {
        List<BankAccount> listOfBankAccount = List.of(BankAccountFake.fake());

        List<BankAccountDTO> listOfBankAccountDTO = bankAccountConverter.convertToListDTO(listOfBankAccount);

        Assertions.assertThat(listOfBankAccountDTO).isNotNull();
        Assertions.assertThat(listOfBankAccountDTO).isNotEmpty();
        Assertions.assertThat(listOfBankAccountDTO.size()).isEqualTo(1);
    }

}
