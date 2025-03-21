package br.com.systec.fintrack.financial.received.impl.service;

import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableException;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableNotFoundException;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.impl.fake.AccountReceivableFake;
import br.com.systec.fintrack.financial.received.impl.metrics.AccountReceivedMetrics;
import br.com.systec.fintrack.financial.received.impl.repository.AccountReceivableRepository;
import br.com.systec.fintrack.financial.received.impl.repository.AccountReceivableRepositoryJPA;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@SpringBootConfiguration
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
    @Mock
    private AccountReceivedMetrics accountReceivedMetrics;
    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @InjectMocks
    private AccountReceivableServiceImpl service;

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

        AccountReceivableVO accountReceivableToSave = AccountReceivableFake.toFakeVO();
        accountReceivableToSave.setId(null);
        accountReceivableToSave.setTenantId(null);

        Mockito.when(repository.save(Mockito.any(AccountReceivable.class))).thenReturn(accountReceivableReturn);

        AccountReceivableVO accountReceivableSave = service.save(accountReceivableToSave);

        Assertions.assertThat(accountReceivableReturn.getId()).isEqualTo(accountReceivableSave.getId());
        Assertions.assertThat(accountReceivableReturn.getAmount()).isEqualTo(accountReceivableSave.getAmount());

        Mockito.verify(repository).save(Mockito.any(AccountReceivable.class));
    }

    @Test
    void whenSaveAccountReceivableGenericException(){

        Mockito.when(repository.save(Mockito.any(AccountReceivable.class)))
                .thenReturn(AccountReceivableFake.toFake());

        Mockito.doThrow(new RuntimeException()).when(bankAccountService)
                .updateBankAccountBalance(Mockito.anyDouble(), Mockito.anyLong(), Mockito.any(TransactionType.class));

        Assertions.assertThatThrownBy(() -> service.save(AccountReceivableFake.toFakeVO()))
                .isInstanceOf(AccountReceivableException.class);
    }

    @Test
    void whenSaveAccountReceivableBaseException(){
        Mockito.doThrow(new BaseException()).when(repository)
                .save(Mockito.any(AccountReceivable.class));

        Assertions.assertThatThrownBy(() -> service.save(AccountReceivableFake.toFakeVO()))
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

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(accountReceivableToReturn));

        AccountReceivableVO accountReceivableReturn = service.findById(Mockito.anyLong());

        Assertions.assertThat(accountReceivableReturn).isNotNull();

        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void whenFindAllAccountReceivableTest() {
        List<AccountReceivable> listOfAccountReceivable = List.of(AccountReceivableFake.toFake());
        Mockito.when(repository.findAll(1L)).thenReturn(listOfAccountReceivable);

        List<AccountReceivableVO> listOfAccountReceivableReturn = service.findAll();

        Assertions.assertThat(listOfAccountReceivableReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountReceivableReturn.size()).isEqualTo(1);

        Mockito.verify(repository).findAll(1L);
    }

    @Test
    void whenFindAccountReceivableByFilter() {
        Page<AccountReceivable> pageToReturn = new PageImpl<>(List.of(AccountReceivableFake.toFake()));

        Mockito.doReturn(pageToReturn).when(repositoryJPA)
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Page<AccountReceivableVO> pageReturned = service.findByFilter(new AccountReceivableFilterVO());

        Assertions.assertThat(pageReturned).isNotNull();
        Assertions.assertThat(pageReturned.getSize()).isEqualTo(pageToReturn.getSize());
        Assertions.assertThat(pageReturned.getTotalElements()).isEqualTo(pageToReturn.getTotalElements());
    }
}
