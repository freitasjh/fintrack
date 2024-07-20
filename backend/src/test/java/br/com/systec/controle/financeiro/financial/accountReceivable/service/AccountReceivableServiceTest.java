package br.com.systec.controle.financeiro.financial.accountReceivable.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountReceivable.fake.AccountReceivableFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class AccountReceivableServiceTest {

    @Mock
    private AccountReceivableRepository repository;
    @Mock
    private RabbitTemplate template;
    @InjectMocks
    private AccountReceivableService service;

    @Test
    void whenSaveAccountReceivableTest() {
        AccountReceivable accountReceivableReturn = AccountReceivableFake.toFake();

        Mockito.when(repository.save(Mockito.any(AccountReceivable.class))).thenReturn(accountReceivableReturn);

        AccountReceivable accountReceivableSave = service.save(AccountReceivableFake.toFake());

        Assertions.assertThat(accountReceivableReturn.getId()).isEqualTo(accountReceivableSave.getId());
        Assertions.assertThat(accountReceivableReturn.getAmount()).isEqualTo(accountReceivableSave.getAmount());

        Mockito.verify(repository).save(Mockito.any(AccountReceivable.class));
    }

    @Test
    void whenFindAllAccountReceivableTest() {
        List<AccountReceivable> listOfAccountReceivable = List.of(AccountReceivableFake.toFake());
        TenantContext.add(1L);
        Mockito.when(repository.findAll(1L)).thenReturn(listOfAccountReceivable);

        List<AccountReceivable> listOfAccountReceivableReturn = service.findAll();

        Assertions.assertThat(listOfAccountReceivableReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountReceivableReturn.size()).isEqualTo(1);

        Mockito.verify(repository).findAll(1L);
    }
}
