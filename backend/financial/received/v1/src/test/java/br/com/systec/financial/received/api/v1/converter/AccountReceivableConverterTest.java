package br.com.systec.financial.received.api.v1.converter;

import br.com.systec.financial.received.api.v1.fake.AccountReceivableFake;
import br.com.systec.fintrack.financial.received.api.v1.converter.AccountReceivableConverter;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableDTO;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountReceivableConverterTest {

    @Test
    void whenConverterAccountReceivableToAccountReceivableDTO() {
        AccountReceivableVO accountReceivable = AccountReceivableFake.toFakeVO();

        AccountReceivableDTO accountReceivableConverted = AccountReceivableConverter.toDTO(accountReceivable);

        Assertions.assertThat(accountReceivableConverted.getId()).isEqualTo(accountReceivable.getId());
        Assertions.assertThat(accountReceivableConverted.getAmount()).isEqualTo(accountReceivable.getAmount());
    }

    @Test
    void whenConverterAccountReceivableVOToAccountReceivableInputDTO() {
        AccountReceivableVO accountReceivableToConverter = AccountReceivableFake.toFakeVO();

        AccountReceivableInputDTO accountReceivableConverted = AccountReceivableConverter.toInputDTO(accountReceivableToConverter);

        Assertions.assertThat(accountReceivableConverted).isNotNull();
        Assertions.assertThat(accountReceivableConverted.getId()).isEqualTo(accountReceivableToConverter.getId());
    }

    @Test
    void whenConverterAccountReceivableInputDTOToAccountReceivableVO() {
        AccountReceivableInputDTO accountReceivableToConverter = AccountReceivableFake.toInputDTO();

        AccountReceivableVO accountReceivableConverted = AccountReceivableConverter.toVO(accountReceivableToConverter);

        Assertions.assertThat(accountReceivableConverted).isNotNull();
        Assertions.assertThat(accountReceivableConverted.getId()).isEqualTo(accountReceivableToConverter.getId());
    }

}
