package br.com.fintrack.financial.recurringtransaction.impl.mapper;

import br.com.fintrack.financial.recurringtransaction.impl.fake.RecurringTransactionFake;
import br.com.systec.fintrack.financial.recurringtransaction.impl.mapper.RecurringTransactionMapper;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecurringTransactionMapperTest {

    @Test
    void whenConverterRecurringTransactionToRecurringTransactionVO() {
        RecurringTransaction recurringTransactionToConverter = RecurringTransactionFake.toFake();

        RecurringTransactionVO recurringTransactionConverted = RecurringTransactionMapper.toVO(recurringTransactionToConverter);

        Assertions.assertThat(recurringTransactionConverted).isNotNull();
        Assertions.assertThat(recurringTransactionConverted.getId()).isEqualTo(recurringTransactionToConverter.getId());
        Assertions.assertThat(recurringTransactionConverted.getTenantId()).isEqualTo(recurringTransactionToConverter.getTenantId());
        Assertions.assertThat(recurringTransactionConverted.getTransactionType()).isEqualTo(recurringTransactionToConverter.getTransactionType());
        Assertions.assertThat(recurringTransactionConverted.isTransactionFixed()).isEqualTo(recurringTransactionToConverter.isTransactionFixed());
        Assertions.assertThat(recurringTransactionConverted.getTotalInstallments()).isEqualTo(recurringTransactionToConverter.getTotalInstallments());
        Assertions.assertThat(recurringTransactionConverted.getCurrentInstallments()).isEqualTo(recurringTransactionToConverter.getCurrentInstallments());
        Assertions.assertThat(recurringTransactionConverted.getFrequencyType()).isEqualTo(recurringTransactionToConverter.getFrequencyType());
    }

    @Test
    void whenConverterRecurringTransactionVOToRecurringTransaction() {
        RecurringTransactionVO recurringTransactionToConverter = RecurringTransactionFake.toFakeVO();

        RecurringTransaction recurringTransactionConverted = RecurringTransactionMapper.toEntity(recurringTransactionToConverter);

        Assertions.assertThat(recurringTransactionConverted).isNotNull();
        Assertions.assertThat(recurringTransactionConverted.getId()).isEqualTo(recurringTransactionToConverter.getId());
        Assertions.assertThat(recurringTransactionConverted.getTenantId()).isEqualTo(recurringTransactionToConverter.getTenantId());
        Assertions.assertThat(recurringTransactionConverted.getTransactionType()).isEqualTo(recurringTransactionToConverter.getTransactionType());
        Assertions.assertThat(recurringTransactionConverted.isTransactionFixed()).isEqualTo(recurringTransactionToConverter.isTransactionFixed());
        Assertions.assertThat(recurringTransactionConverted.getTotalInstallments()).isEqualTo(recurringTransactionToConverter.getTotalInstallments());
        Assertions.assertThat(recurringTransactionConverted.getCurrentInstallments()).isEqualTo(recurringTransactionToConverter.getCurrentInstallments());
        Assertions.assertThat(recurringTransactionConverted.getFrequencyType()).isEqualTo(recurringTransactionToConverter.getFrequencyType());
    }
}
