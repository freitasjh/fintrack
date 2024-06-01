package br.com.systec.controle.financeiro.accountReceivable.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.fake.ReceiveFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.repository.AccountReceivableRepository;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ReceiveServiceTest {
    @Mock
    private AccountReceivableRepository repository;

    @InjectMocks
    private AccountReceivableService service;

    @BeforeAll
    static void init(){
        TenantContext.add(1L);
    }

    @Test
    void whenSaveReceive() {
        AccountReceivable receiveToSave = ReceiveFake.fake();
        AccountReceivable receiveToReturn = ReceiveFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.save(receiveToSave)).thenReturn(receiveToReturn);

        AccountReceivable receiveReturn = service.save(receiveToSave);

        Assertions.assertThat(receiveReturn).isNotNull();
        Assertions.assertThat(receiveReturn.getId()).isEqualTo(receiveToReturn.getId());
        Assertions.assertThat(receiveReturn.getDescription()).isEqualTo(receiveToReturn.getDescription());
        Assertions.assertThat(receiveReturn.getAmount()).isEqualTo(receiveToReturn.getAmount());
        Assertions.assertThat(receiveReturn.getCategory().getId()).isEqualTo(receiveToReturn.getCategory().getId());
        Assertions.assertThat(receiveReturn.getAccountId()).isEqualTo(receiveToReturn.getAccountId());
        Assertions.assertThat(receiveReturn.getTenantId()).isEqualTo(receiveToReturn.getTenantId());
        Assertions.assertThat(receiveReturn.getDateReceiver()).isEqualTo(receiveToReturn.getDateReceiver());

        Mockito.verify(repository).save(receiveToSave);
    }

    @Test
    void whenFindReceiveById() {
        AccountReceivable receiveToReturn = ReceiveFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(receiveToReturn));

        AccountReceivable receiveReturn = service.findReceiveById(1L);

        Assertions.assertThat(receiveReturn).isNotNull();
        Assertions.assertThat(receiveReturn.getId()).isEqualTo(receiveToReturn.getId());
        Assertions.assertThat(receiveReturn.getDescription()).isEqualTo(receiveToReturn.getDescription());
        Assertions.assertThat(receiveReturn.getAmount()).isEqualTo(receiveToReturn.getAmount());
        Assertions.assertThat(receiveReturn.getCategory().getId()).isEqualTo(receiveToReturn.getCategory().getId());
        Assertions.assertThat(receiveReturn.getAccountId()).isEqualTo(receiveToReturn.getAccountId());
        Assertions.assertThat(receiveReturn.getTenantId()).isEqualTo(receiveToReturn.getTenantId());
        Assertions.assertThat(receiveReturn.getDateReceiver()).isEqualTo(receiveToReturn.getDateReceiver());

        Mockito.verify(repository).findById(1L);
    }

    @Test
    void whenFindReceiveByIdObjectNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findReceiveById(1L)).isInstanceOf(ObjectNotFoundException.class)
                .as(I18nTranslate.toLocale("receive.not.found"));

        Mockito.verify(repository).findById(1L);
    }

    @Test
    void whenUpdateReceive() {
        AccountReceivable receiveToUpdate = ReceiveFake.fake();
        receiveToUpdate.setId(1L);

        AccountReceivable receiveToReturn = ReceiveFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.update(receiveToUpdate)).thenReturn(receiveToReturn);

        AccountReceivable receiveReturn = service.update(receiveToUpdate);

        Assertions.assertThat(receiveReturn).isNotNull();
        Assertions.assertThat(receiveReturn.getId()).isEqualTo(receiveToReturn.getId());
        Assertions.assertThat(receiveReturn.getDescription()).isEqualTo(receiveToReturn.getDescription());
        Assertions.assertThat(receiveReturn.getAmount()).isEqualTo(receiveToReturn.getAmount());
        Assertions.assertThat(receiveReturn.getCategory().getId()).isEqualTo(receiveToReturn.getCategory().getId());
        Assertions.assertThat(receiveReturn.getAccountId()).isEqualTo(receiveToReturn.getAccountId());
        Assertions.assertThat(receiveReturn.getTenantId()).isEqualTo(receiveToReturn.getTenantId());
        Assertions.assertThat(receiveReturn.getDateReceiver()).isEqualTo(receiveToReturn.getDateReceiver());

        Mockito.verify(repository).update(receiveToUpdate);
    }

    @Test
    void whenDeleteReceive() {
        AccountReceivable receiveToDelete = ReceiveFake.fake();
        receiveToDelete.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(receiveToDelete));

        service.delete(1L);

        Mockito.verify(repository).findById(1L);
    }
}
