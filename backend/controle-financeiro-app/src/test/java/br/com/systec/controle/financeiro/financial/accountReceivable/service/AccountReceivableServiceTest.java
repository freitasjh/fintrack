package br.com.systec.controle.financeiro.financial.accountReceivable.service;

import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableException;
import br.com.systec.controle.financeiro.financial.accountReceivable.exceptions.AccountReceivableNotFoundException;
import br.com.systec.controle.financeiro.financial.accountReceivable.fake.AccountReceivableFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepositoryJPA;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountReceivableServiceTest {

    @Mock
    private AccountReceivableRepository repository;
    @Mock
    private AccountReceivableRepositoryJPA repositoryJPA;
    @Mock
    private RabbitTemplate template;
    @Mock
    private BankAccountService bankAccountService;
    @InjectMocks
    private AccountReceivableService service;

    @BeforeAll
    public static void init() {
        TenantContext.add(1L);
    }

    @AfterAll
    public static void close(){
        TenantContext.clear();
    }

    @Test
    void whenSaveAccountReceivableTest() {
        AccountReceivable accountReceivableReturn = AccountReceivableFake.toFake();

        AccountReceivable accountReceivableToSave = AccountReceivableFake.toFake();
        accountReceivableToSave.setId(null);
        accountReceivableToSave.setTenantId(null);

        Mockito.when(repository.save(Mockito.any(AccountReceivable.class))).thenReturn(accountReceivableReturn);

        AccountReceivable accountReceivableSave = service.save(accountReceivableToSave);

        Assertions.assertThat(accountReceivableReturn.getId()).isEqualTo(accountReceivableSave.getId());
        Assertions.assertThat(accountReceivableReturn.getAmount()).isEqualTo(accountReceivableSave.getAmount());

        Mockito.verify(repository).save(Mockito.any(AccountReceivable.class));
    }

    @Test
    void whenSaveAccountReceivableGenericException(){

        Mockito.when(repository.save(Mockito.any(AccountReceivable.class)))
                .thenReturn(AccountReceivableFake.toFake());

        Mockito.doThrow(new RuntimeException()).when(bankAccountService)
                .updateBankAccountBalance(Mockito.any(AccountReceivable.class));

        Assertions.assertThatThrownBy(() -> service.save(AccountReceivableFake.toFake()))
                .isInstanceOf(AccountReceivableException.class);
    }

    @Test
    void whenSaveAccountReceivableBaseException(){
        Mockito.doThrow(new BaseException()).when(repository)
                .save(Mockito.any(AccountReceivable.class));

        Assertions.assertThatThrownBy(() -> service.save(AccountReceivableFake.toFake()))
                .isInstanceOf(BaseException.class);
    }

    @Test
    void whenFindByIdAndObjectNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(AccountReceivableNotFoundException.class);
    }

    @Test
    void whenFindById() {
        AccountReceivable accountReceivableToReturn = AccountReceivableFake.toFake();

        Mockito.doReturn(Optional.of(accountReceivableToReturn))
                .when(repository).findById(Mockito.anyLong());

        AccountReceivable accountReceivableReturn = service.findById(1L);

        Assertions.assertThat(accountReceivableReturn).isNotNull();
    }

    @Test
    void whenFindAllAccountReceivableTest() {
        List<AccountReceivable> listOfAccountReceivable = List.of(AccountReceivableFake.toFake());
        Mockito.when(repository.findAll(1L)).thenReturn(listOfAccountReceivable);

        List<AccountReceivable> listOfAccountReceivableReturn = service.findAll();

        Assertions.assertThat(listOfAccountReceivableReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountReceivableReturn.size()).isEqualTo(1);

        Mockito.verify(repository).findAll(1L);
    }

    @Test
    void whenFindAccountReceivableByFilter() {
        Page<AccountReceivable> pageToReturn = new PageImpl<>(List.of(AccountReceivableFake.toFake()));

        Mockito.doReturn(pageToReturn).when(repositoryJPA)
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Page<AccountReceivable> pageReturned = service.findByFilter(new AccountReceivableFilterVO());

        Assertions.assertThat(pageReturned).isNotNull();
        Assertions.assertThat(pageReturned.getSize()).isEqualTo(pageToReturn.getSize());
        Assertions.assertThat(pageReturned.getTotalElements()).isEqualTo(pageToReturn.getTotalElements());
    }
}
