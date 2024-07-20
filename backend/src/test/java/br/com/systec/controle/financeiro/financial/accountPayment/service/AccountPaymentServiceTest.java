package br.com.systec.controle.financeiro.financial.accountPayment.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountPayment.fake.AccountPaymentFake;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.accountPayment.repository.AccountPaymentRepository;
import br.com.systec.controle.financeiro.financial.accountPayment.repository.AccountPaymentRepositoryJpa;
import ch.qos.logback.core.testUtil.MockInitialContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
public class AccountPaymentServiceTest {

    @Mock
    private AccountPaymentRepository repository;
    @Mock
    private AccountPaymentRepositoryJpa repositoryJpa;
    @Mock
    private RabbitTemplate template;

    @InjectMocks
    private AccountPaymentService service;

    @BeforeAll
    static void init() {
        TenantContext.add(1L);
    }

    @Test
    void whenSaveNewAccountPayment() {
        AccountPayment accountPaymentReturn = AccountPaymentFake.toFake();

        Mockito.when(repository.save(Mockito.any())).thenReturn(accountPaymentReturn);

        AccountPayment accountPaymentSaved = service.save(AccountPaymentFake.toFake());

        Assertions.assertThat(accountPaymentSaved).isNotNull();

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void whenFindAllAccountPaymentService() {
        List<AccountPayment> listOfAccountPaymentToReturn = List.of(AccountPaymentFake.toFake());

        Mockito.when(repository.findAllByTenant()).thenReturn(listOfAccountPaymentToReturn);

        List<AccountPayment> listOfAccountPaymentReturn = service.listAllPayment();

        Assertions.assertThat(listOfAccountPaymentReturn).isNotNull();
        Assertions.assertThat(listOfAccountPaymentReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountPaymentReturn.size()).isEqualTo(listOfAccountPaymentToReturn.size());

        Mockito.verify(repository).findAllByTenant();
    }
}
