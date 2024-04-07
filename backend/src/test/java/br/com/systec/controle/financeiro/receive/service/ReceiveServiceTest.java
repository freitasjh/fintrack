package br.com.systec.controle.financeiro.receive.service;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.fake.ReceiveFake;
import br.com.systec.controle.financeiro.receive.model.Receive;
import br.com.systec.controle.financeiro.receive.repository.ReceiveRepository;
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
    private ReceiveRepository repository;

    @InjectMocks
    private ReceiveService service;

    @BeforeAll
    static void init(){
        TenantContext.add(1L);
    }

    @Test
    void whenSaveReceive() {
        Receive receiveToSave = ReceiveFake.fake();
        Receive receiveToReturn = ReceiveFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.save(receiveToSave)).thenReturn(receiveToReturn);

        Receive receiveReturn = service.save(receiveToSave);

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
        Receive receiveToReturn = ReceiveFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(receiveToReturn));

        Receive receiveReturn = service.findReceiveById(1L);

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
        Receive receiveToUpdate = ReceiveFake.fake();
        receiveToUpdate.setId(1L);

        Receive receiveToReturn = ReceiveFake.fake();
        receiveToReturn.setId(1L);

        Mockito.when(repository.update(receiveToUpdate)).thenReturn(receiveToReturn);

        Receive receiveReturn = service.update(receiveToUpdate);

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
        Receive receiveToDelete = ReceiveFake.fake();
        receiveToDelete.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(receiveToDelete));

        service.delete(1L);

        Mockito.verify(repository).findById(1L);
    }
}
