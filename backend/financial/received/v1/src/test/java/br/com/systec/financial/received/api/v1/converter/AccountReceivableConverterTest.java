package br.com.systec.financial.received.api.v1.converter;

import br.com.systec.financial.received.api.v1.fake.AccountReceivableFake;
import br.com.systec.fintrack.financial.received.api.v1.converter.AccountReceivableConverter;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableDTO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountReceivableConverterTest {

    @Test
    void whenConverterAccountReceivableToAccountReceivableDTO() {
        AccountReceivable accountReceivable = AccountReceivableFake.toFake();

        AccountReceivableDTO accountReceivableConverted = AccountReceivableConverter.toDTO(accountReceivable);

        Assertions.assertThat(accountReceivableConverted.getId()).isEqualTo(accountReceivable.getId());
        Assertions.assertThat(accountReceivableConverted.getAmount()).isEqualTo(accountReceivable.getAmount());
    }
}
