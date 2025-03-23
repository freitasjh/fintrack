package br.com.systec.fintrack.financial.payment.impl.service;

import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
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
    void whenSaveNewAccountPaymentAndDueDateIsNullException() {
        AccountPayment accountPaymentToSave = AccountPaymentFake.toFake();
        accountPaymentToSave.setPaymentDueDate(null);
        accountPaymentToSave.setDateProcessed(null);

        Assertions.assertThatThrownBy(() -> service.save(accountPaymentToSave))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    void whenSaveNewAccountPaymentException() {
        AccountPayment accountPaymentToSave = AccountPaymentFake.toFake();

        Mockito.when(repository.save(Mockito.any())).thenThrow(new RuntimeException());

        Assertions.assertThatThrownBy(() -> service.save(accountPaymentToSave))
                .isInstanceOf(BaseException.class);

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

    @Test
    void whenFindTenLastPayment() {
        List<AccountPayment> listOfAccountPaymentToReturn = List.of(AccountPaymentFake.toFake());

        Mockito.doReturn(listOfAccountPaymentToReturn).when(repository).findLastTenPayment();

        List<AccountPayment> listAccountPaymentReturn = service.findLastTenPayment();

        Assertions.assertThat(listAccountPaymentReturn).isNotNull();
        Assertions.assertThat(listAccountPaymentReturn).isNotEmpty();
        Assertions.assertThat(listAccountPaymentReturn.size()).isEqualTo(listOfAccountPaymentToReturn.size());
        Assertions.assertThat(listAccountPaymentReturn.get(0).getId()).isEqualTo(listOfAccountPaymentToReturn.get(0).getId());

        Mockito.verify(repository).findLastTenPayment();
    }

    @Test
    void whenFindAccountPaymentOpen() {
        AccountPayment accountPaymentToReturn = AccountPaymentFake.toFake();
        accountPaymentToReturn.setDateProcessed(null);
        accountPaymentToReturn.setProcessed(false);

        List<AccountPayment> listOfAccountPaymentToReturn = List.of(accountPaymentToReturn);

        Mockito.doReturn(listOfAccountPaymentToReturn).when(repository).findAccountPaymentOpen();

        List<AccountPayment> listOfAccountPaymentReturn = service.findAccountPaymentOpen();

        Assertions.assertThat(listOfAccountPaymentReturn).isNotNull();
        Assertions.assertThat(listOfAccountPaymentReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountPaymentReturn.size()).isEqualTo(listOfAccountPaymentToReturn.size());
        Assertions.assertThat(listOfAccountPaymentReturn.get(0).getId()).isEqualTo(listOfAccountPaymentToReturn.get(0).getId());
        Assertions.assertThat(listOfAccountPaymentReturn.get(0).isProcessed()).isEqualTo(listOfAccountPaymentToReturn.get(0).isProcessed());

        Mockito.verify(repository).findAccountPaymentOpen();
    }

}
