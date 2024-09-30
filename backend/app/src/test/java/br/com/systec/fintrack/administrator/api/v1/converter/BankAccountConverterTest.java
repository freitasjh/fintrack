package br.com.systec.fintrack.administrator.api.v1.converter;

import br.com.systec.fintrack.administrator.bankAccount.api.v1.converter.BankAccountConverter;
import br.com.systec.fintrack.administrator.bankAccount.api.v1.dto.BankAccountDTO;
import br.com.systec.fintrack.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.fake.BankAccountFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class BankAccountConverterTest {

    @Autowired
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
