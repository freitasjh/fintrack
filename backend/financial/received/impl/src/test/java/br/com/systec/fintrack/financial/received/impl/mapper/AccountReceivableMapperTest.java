package br.com.systec.fintrack.financial.received.impl.mapper;

import br.com.systec.fintrack.financial.received.impl.fake.AccountReceivableFake;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountReceivableMapperTest {

    @Test
    void whenConverterAccountReceivableTOAccountReceivableVO() {
        AccountReceivable accountReceivable = AccountReceivableFake.toFake();

        AccountReceivableVO accountReceivableVO = AccountReceivableMapper.toVO(accountReceivable);

        Assertions.assertThat(accountReceivableVO.getId()).isEqualTo(accountReceivable.getId());
        Assertions.assertThat(accountReceivableVO.getDescription()).isEqualTo(accountReceivable.getDescription());
        Assertions.assertThat(accountReceivableVO.getAmount()).isEqualTo(accountReceivable.getAmount());
        Assertions.assertThat(accountReceivableVO.getCategoryTransactionType()).isEqualTo(accountReceivable.getCategoryTransactionType());
        Assertions.assertThat(accountReceivableVO.getDateProcessed()).isEqualTo(accountReceivable.getDateProcessed());
        Assertions.assertThat(accountReceivableVO.getDateRegister()).isEqualTo(accountReceivable.getDateRegister());
        Assertions.assertThat(accountReceivableVO.getBankAccount().getId()).isEqualTo(accountReceivable.getBankAccount().getId());
        Assertions.assertThat(accountReceivableVO.getTransactionType()).isEqualTo(accountReceivable.getTransactionType());
    }

    @Test
    void whenConverterAccountReceivableVOTOAccountReceivable() {
        AccountReceivableVO accountReceivableToConverter = AccountReceivableFake.toFakeVO();

        AccountReceivable accountReceivableConverted = AccountReceivableMapper.toEntity(accountReceivableToConverter);

        Assertions.assertThat(accountReceivableConverted.getId()).isEqualTo(accountReceivableToConverter.getId());
        Assertions.assertThat(accountReceivableConverted.getDescription()).isEqualTo(accountReceivableToConverter.getDescription());
        Assertions.assertThat(accountReceivableConverted.getAmount()).isEqualTo(accountReceivableToConverter.getAmount());
        Assertions.assertThat(accountReceivableConverted.getCategoryTransactionType()).isEqualTo(accountReceivableToConverter.getCategoryTransactionType());
        Assertions.assertThat(accountReceivableConverted.getDateProcessed()).isEqualTo(accountReceivableToConverter.getDateProcessed());
        Assertions.assertThat(accountReceivableConverted.getDateRegister()).isEqualTo(accountReceivableToConverter.getDateRegister());
        Assertions.assertThat(accountReceivableConverted.getBankAccount().getId()).isEqualTo(accountReceivableToConverter.getBankAccount().getId());
        Assertions.assertThat(accountReceivableConverted.getTransactionType()).isEqualTo(accountReceivableToConverter.getTransactionType());
    }
}
