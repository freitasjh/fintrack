package br.com.systec.controle.financeiro.creditCard;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.creditCard.api.v1.mapper.CreditCardMapper;
import br.com.systec.controle.financeiro.creditCard.fake.CreditCardFake;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import br.com.systec.controle.financeiro.creditCard.repository.CreditCardRepository;
import br.com.systec.controle.financeiro.creditCard.repository.CreditCardRepositoryJPA;
import br.com.systec.controle.financeiro.creditCard.service.CreditCardService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
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
public class CreditCardServiceTest {

    @Mock
    private CreditCardRepositoryJPA repositoryJPA;
    @Mock
    private CreditCardRepository repository;
    @InjectMocks
    private CreditCardService service;

    @BeforeAll
    public static void init(){
        TenantContext.add(1L);
    }

    @AfterAll
    public static void close() {
        TenantContext.clear();
    }

    @Test
    void whenSaveCreditCard() {
        CreditCard creditCardToReturn = CreditCardFake.toFake();

        Mockito.doReturn(creditCardToReturn).when(repository).save(Mockito.any(CreditCard.class));

        CreditCard creditCardSaved = service.save(CreditCardFake.toFake());

        Assertions.assertThat(creditCardSaved).isNotNull();
        Assertions.assertThat(creditCardToReturn.getId()).isEqualTo(creditCardSaved.getId());

        Mockito.verify(repository).save(Mockito.any(CreditCard.class));
    }

    @Test
    void whenUpdateCreditCardObjectNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.update(CreditCardFake.toFake(), 1L)).isInstanceOf(ObjectNotFoundException.class);

        Mockito.verify(repository).findById(Mockito.anyLong());
    }


}
