package br.com.systec.controle.financeiro.accountReceivable.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.accountReceivable.fake.AccountReceivableFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepositoryJPA;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AccountReceivableServiceTest {
    @Mock
    private AccountReceivableRepository repository;
    @Mock
    private AccountReceivableRepositoryJPA repositoryJPA;

    @InjectMocks
    private AccountReceivableService service;

    @BeforeAll
    static void init(){
        TenantContext.add(1L);
    }

    @Test
    void whenSaveReceive() {
        AccountReceivable accountReceivablereceiveToSave = AccountReceivableFake.fake();
        AccountReceivable receiveToReturn = AccountReceivableFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.save(accountReceivablereceiveToSave)).thenReturn(receiveToReturn);

        AccountReceivable receiveReturn = service.save(accountReceivablereceiveToSave);

        Assertions.assertThat(receiveReturn).isNotNull();
        Assertions.assertThat(receiveReturn.getId()).isEqualTo(receiveToReturn.getId());
        Assertions.assertThat(receiveReturn.getDescription()).isEqualTo(receiveToReturn.getDescription());
        Assertions.assertThat(receiveReturn.getAmount()).isEqualTo(receiveToReturn.getAmount());

        Assertions.assertThat(receiveReturn.getAccountId()).isEqualTo(receiveToReturn.getAccountId());
        Assertions.assertThat(receiveReturn.getTenantId()).isEqualTo(receiveToReturn.getTenantId());
        Assertions.assertThat(receiveReturn.getDateReceiver()).isEqualTo(receiveToReturn.getDateReceiver());

        Mockito.verify(repository).save(accountReceivablereceiveToSave);
    }

    @Test
    void whenFindReceiveById() {
        AccountReceivable receiveToReturn = AccountReceivableFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(receiveToReturn));

        AccountReceivable receiveReturn = service.findAccountReceivableById(1L);

        Assertions.assertThat(receiveReturn).isNotNull();
        Assertions.assertThat(receiveReturn.getId()).isEqualTo(receiveToReturn.getId());
        Assertions.assertThat(receiveReturn.getDescription()).isEqualTo(receiveToReturn.getDescription());
        Assertions.assertThat(receiveReturn.getAmount()).isEqualTo(receiveToReturn.getAmount());
        Assertions.assertThat(receiveReturn.getAccountId()).isEqualTo(receiveToReturn.getAccountId());
        Assertions.assertThat(receiveReturn.getTenantId()).isEqualTo(receiveToReturn.getTenantId());
        Assertions.assertThat(receiveReturn.getDateReceiver()).isEqualTo(receiveToReturn.getDateReceiver());

        Mockito.verify(repository).findById(1L);
    }

    @Test
    void whenFindReceiveByIdObjectNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findAccountReceivableById(1L)).isInstanceOf(ObjectNotFoundException.class)
                .as(I18nTranslate.toLocale("accountReceivable.not.found"));

        Mockito.verify(repository).findById(1L);
    }

    @Test
    void whenUpdateReceive() {
        AccountReceivable receiveToUpdate = AccountReceivableFake.fake();
        receiveToUpdate.setId(1L);

        AccountReceivable receiveToReturn = AccountReceivableFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.update(receiveToUpdate)).thenReturn(receiveToReturn);

        AccountReceivable receiveReturn = service.update(receiveToUpdate);

        Assertions.assertThat(receiveReturn).isNotNull();
        Assertions.assertThat(receiveReturn.getId()).isEqualTo(receiveToReturn.getId());
        Assertions.assertThat(receiveReturn.getDescription()).isEqualTo(receiveToReturn.getDescription());
        Assertions.assertThat(receiveReturn.getAmount()).isEqualTo(receiveToReturn.getAmount());
        Assertions.assertThat(receiveReturn.getAccountId()).isEqualTo(receiveToReturn.getAccountId());
        Assertions.assertThat(receiveReturn.getTenantId()).isEqualTo(receiveToReturn.getTenantId());
        Assertions.assertThat(receiveReturn.getDateReceiver()).isEqualTo(receiveToReturn.getDateReceiver());

        Mockito.verify(repository).update(receiveToUpdate);
    }

    @Test
    void whenDeleteReceive() {
        AccountReceivable receiveToDelete = AccountReceivableFake.fake();
        receiveToDelete.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(receiveToDelete));

        service.delete(1L);

        Mockito.verify(repository).findById(1L);
    }

    @Test
    void whenFilterAccountReceivablePageable(){
        Page<AccountReceivable> pageOfAccountReceivableReturn = new PageImpl<>(List.of(AccountReceivableFake.fake()));
        AccountReceivableFilterVO filterVO = new AccountReceivableFilterVO();

        Mockito.when(repositoryJPA.findAll(filterVO.getSpecification(), filterVO.getPageable())).thenReturn(pageOfAccountReceivableReturn);

        Page<AccountReceivable> pageOfAccountReceivable = service.findByFilter(filterVO);

        Assertions.assertThat(pageOfAccountReceivable).isNotNull();
        Assertions.assertThat(pageOfAccountReceivable.getContent()).isNotEmpty();
        Assertions.assertThat(pageOfAccountReceivable.getSize()).isEqualTo(pageOfAccountReceivableReturn.getSize());

        Mockito.verify(repositoryJPA).findAll(filterVO.getSpecification(), filterVO.getPageable());
    }
}
