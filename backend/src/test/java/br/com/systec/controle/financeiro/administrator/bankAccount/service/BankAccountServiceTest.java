package br.com.systec.controle.financeiro.administrator.bankAccount.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.filter.BankAccountFilterVO;
import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.administrator.bankAccount.repository.BankAccountRepository;
import br.com.systec.controle.financeiro.administrator.bankAccount.repository.BankAccountRepositoryJPA;
import br.com.systec.controle.financeiro.fake.BankAccountFake;
import br.com.systec.controle.financeiro.fake.ReceiveFake;
import br.com.systec.controle.financeiro.receive.model.Receive;
import br.com.systec.controle.financeiro.receive.service.ReceiveService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository repository;
    @Mock
    private BankAccountRepositoryJPA repositoryJPA;
    @Mock
    private ReceiveService receiveService;

    @InjectMocks
    private BankAccountService service;

    @Test
    void whenSaveNewBakAccountTest() {
        BankAccount bankAccountToReturn = BankAccountFake.fake();
        BankAccount bankAccountToSave = BankAccountFake.fake();

        bankAccountToSave.setId(null);
        bankAccountToSave.setTenantId(null);
        bankAccountToSave.setBalance(100);

        Mockito.doReturn(bankAccountToReturn).when(repository).save(Mockito.any(BankAccount.class));
        Mockito.when(receiveService.save(Mockito.any(Receive.class))).thenReturn(ReceiveFake.fake());

        BankAccount bankAccountSaved = service.save(bankAccountToSave);

        Assertions.assertThat(bankAccountToReturn.getDescription()).isEqualTo(bankAccountSaved.getDescription());

        Mockito.verify(repository).save(Mockito.any(BankAccount.class));
        Mockito.verify(receiveService).save(Mockito.any(Receive.class));
    }

    @Test
    void whenFindAndReturnPage() {
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
