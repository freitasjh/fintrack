package br.com.systec.fintrack.administrator.bankAccount.service;

import br.com.systec.fintrack.administrator.bankAccount.filter.BankAccountFilterVO;
import br.com.systec.fintrack.administrator.bankAccount.model.BankAccount;
import br.com.systec.fintrack.administrator.bankAccount.repository.BankAccountRepository;
import br.com.systec.fintrack.administrator.bankAccount.repository.BankAccountRepositoryJPA;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.fake.BankAccountFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository repository;
    @Mock
    private BankAccountRepositoryJPA repositoryJPA;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private BankAccountService service;

    @Test
    void whenSaveNewAccountTest() {
        TenantContext.add(1L);
        BankAccount bankAccountToReturn = BankAccountFake.fake();
        bankAccountToReturn.setInitialValue(1000.0);
        BankAccount bankAccountToSave = BankAccountFake.fake();

        bankAccountToSave.setId(null);
        bankAccountToSave.setInitialValue(1000.0);

        Mockito.doReturn(bankAccountToReturn).when(repository).save(Mockito.any(BankAccount.class));

        BankAccount bankAccountSaved = service.save(bankAccountToSave);

        Assertions.assertThat(bankAccountSaved).isNotNull();
        Assertions.assertThat(bankAccountSaved.getId()).isNotNull();
        Assertions.assertThat(bankAccountSaved.getInitialValue()).isEqualTo(bankAccountToSave.getInitialValue());

        Mockito.verify(repository).save(Mockito.any(BankAccount.class));
    }

    @Test
    void whenFindAndReturnPage() {
        TenantContext.add(1L);
        BankAccount bankAccount = BankAccountFake.fake();
        Page<BankAccount> pageBankAccount = new PageImpl<>(List.of(bankAccount));
        BankAccountFilterVO filterVO = new BankAccountFilterVO(30, 0, "");

        Mockito.doReturn(pageBankAccount).when(repositoryJPA).
                findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Page<BankAccount> pageBankAccountReturn = service.findByFilter(filterVO);

        Assertions.assertThat(pageBankAccountReturn).isNotNull();
        Assertions.assertThat(pageBankAccountReturn.getContent()).isNotEmpty();
        Assertions.assertThat(pageBankAccountReturn.getSize()).isEqualTo(1);

        Mockito.verify(repositoryJPA).findAll(Mockito.any(Specification.class),
                Mockito.any(Pageable.class));
    }
}
