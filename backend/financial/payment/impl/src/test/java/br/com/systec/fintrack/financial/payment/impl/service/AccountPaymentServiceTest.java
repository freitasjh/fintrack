package br.com.systec.fintrack.financial.payment.impl.service;

import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.ValidatorException;
import br.com.systec.fintrack.financial.payment.impl.repository.AccountPaymentRepository;
import br.com.systec.fintrack.financial.payment.impl.repository.AccountPaymentRepositoryJpa;
import br.com.systec.fintrack.financial.payment.impl.fake.AccountPaymentFake;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@SpringBootConfiguration
public class AccountPaymentServiceTest {

    @Mock
    private AccountPaymentRepository repository;
    @Mock
    private AccountPaymentRepositoryJpa repositoryJpa;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private RabbitTemplate template;

    @InjectMocks
    private AccountPaymentServiceImpl service = Mockito.spy(new AccountPaymentServiceImpl());

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
    void whenSaveNewAccountAndDateProcessedAndPaymentDueDateNullException() {
        AccountPayment accountPayment = AccountPaymentFake.toFake();
        accountPayment.setDateProcessed(null);
        accountPayment.setPaymentDueDate(null);

        Assertions.assertThatThrownBy(() -> service.save(accountPayment))
                .isInstanceOf(ValidatorException.class);
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
