package br.com.systec.fintrack.financial.transfer.api.v1.converter;

import br.com.systec.fintrack.financial.transfer.api.v1.fake.AccountTransferFake;
import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;
import br.com.systec.fintrack.financial.transfer.v1.converter.AccountTransferMapper;
import br.com.systec.fintrack.financial.transfer.v1.dto.AccountTransferInputDTO;
import br.com.systec.fintrack.financial.transfer.v1.dto.AccountTransferResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AccountTransferMapperTest {

    @Test
    void whenConverterAccountTransferInputDtoToEntity() {
        AccountTransferInputDTO accountTransferInputDTO = AccountTransferFake.toInputDTO();

        AccountTransfer accountTransferConverter = AccountTransferMapper.toEntity(accountTransferInputDTO);

        Assertions.assertThat(accountTransferConverter).isNotNull();
        Assertions.assertThat(accountTransferConverter.getId()).isEqualTo(accountTransferInputDTO.getId());
        Assertions.assertThat(accountTransferConverter.getBankAccountTo().getId()).isEqualTo(accountTransferInputDTO.getBankAccountToId());
        Assertions.assertThat(accountTransferConverter.getBankAccountFrom().getId()).isEqualTo(accountTransferInputDTO.getBankAccountFromId());
        Assertions.assertThat(accountTransferConverter.getAmount()).isEqualTo(accountTransferInputDTO.getAmount());
    }

    @Test
    void whenConverterAccountTransferToAccountTransferInputDTO() {
        AccountTransfer accountTransfer = AccountTransferFake.toFake();

        AccountTransferInputDTO accountTransferConverted = AccountTransferMapper.toInputDTO(accountTransfer);

        Assertions.assertThat(accountTransferConverted).isNotNull();
        Assertions.assertThat(accountTransferConverted.getId()).isEqualTo(accountTransfer.getId());
        Assertions.assertThat(accountTransferConverted.getDescription()).isEqualTo(accountTransfer.getDescription());
        Assertions.assertThat(accountTransferConverted.getTransferDate()).isEqualTo(accountTransfer.getTransferDate());
        Assertions.assertThat(accountTransferConverted.getBankAccountFromId()).isEqualTo(accountTransfer.getBankAccountFrom().getId());
        Assertions.assertThat(accountTransferConverted.getBankAccountToId()).isEqualTo(accountTransfer.getBankAccountTo().getId());
    }

    @Test
    void whenConverterAccountTransferToAccountTransferResponseDTO() {
        AccountTransfer accountTransfer = AccountTransferFake.toFake();

        AccountTransferResponseDTO responseDTO = AccountTransferMapper.toResponseDTO(accountTransfer);

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getId()).isEqualTo(accountTransfer.getId());
        Assertions.assertThat(responseDTO.getDescription()).isEqualTo(accountTransfer.getDescription());
        Assertions.assertThat(responseDTO.getAmount()).isEqualTo(accountTransfer.getAmount());
        Assertions.assertThat(responseDTO.getBankFromDescription()).isEqualTo(accountTransfer.getBankAccountFrom().getDescription());
        Assertions.assertThat(responseDTO.getBankToDescription()).isEqualTo(accountTransfer.getBankAccountTo().getDescription());
        Assertions.assertThat(responseDTO.getTransferDate()).isEqualTo(accountTransfer.getTransferDate());
    }

    @Test
    void whenConverterListAccountTransferToListAccountTransferResponseDTO() {
        List<AccountTransfer> listOfAccountTransfer = List.of(AccountTransferFake.toFake());

        List<AccountTransferResponseDTO> listOfAccountTransferResponseDTO = AccountTransferMapper.toListResponseDTO(listOfAccountTransfer);

        Assertions.assertThat(listOfAccountTransferResponseDTO).isNotNull();
        Assertions.assertThat(listOfAccountTransferResponseDTO).isNotEmpty();
    }
}
