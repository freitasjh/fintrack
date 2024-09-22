package br.com.systec.fintrack.creditCard;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.creditCard.fake.CreditCardFake;
import br.com.systec.fintrack.creditCard.filter.CreditCardFilterVO;
import br.com.systec.fintrack.creditCard.model.CreditCard;
import br.com.systec.fintrack.creditCard.repository.CreditCardRepository;
import br.com.systec.fintrack.creditCard.repository.CreditCardRepositoryJPA;
import br.com.systec.fintrack.creditCard.service.CreditCardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void whenSaveNewCreditAndException() {
        Mockito.doThrow(new RuntimeException()).when(repository)
                .save(Mockito.any(CreditCard.class));

        Assertions.assertThatThrownBy(() -> service.save(CreditCardFake.toFake()))
                .isInstanceOf(BaseException.class);
    }

    @Test
    void whenFindAllCreditCard(){
        List<CreditCard> listOfCreditCardToReturn = List.of(CreditCardFake.toFake());

        Mockito.doReturn(listOfCreditCardToReturn).when(repository).findAllByTenantId();

        List<CreditCard> listCreditCardReturn = service.findAll();

        Assertions.assertThat(listCreditCardReturn).isNotNull();
        Assertions.assertThat(listCreditCardReturn).isNotEmpty();
        Assertions.assertThat(listCreditCardReturn.size()).isEqualTo(listOfCreditCardToReturn.size());

        Mockito.verify(repository).findAllByTenantId();
    }

    @Test
    void whenFindByFilter() {
        Page<CreditCard> pageOfFilterToReturn = new PageImpl<>(List.of(CreditCardFake.toFake()));

        Mockito.doReturn(pageOfFilterToReturn).when(repositoryJPA)
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Page<CreditCard> pageOfFilterReturn = service.findByFilter(new CreditCardFilterVO(30,0,null));

        Assertions.assertThat(pageOfFilterReturn).isNotNull();
        Assertions.assertThat(pageOfFilterReturn.getTotalElements()).isEqualTo(pageOfFilterToReturn.getTotalElements());
    }

    @Test
    void wheFindByFilterAndReturnExceptionToFind() {
        Mockito.doThrow(new RuntimeException()).when(repositoryJPA)
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Assertions.assertThatThrownBy(() -> service.findByFilter(new CreditCardFilterVO(30,0,null)))
                .isInstanceOf(BaseException.class);
    }

    @Test
    void whenFindCreditCardById() {
        CreditCard creditCardToReturn = CreditCardFake.toFake();

        Mockito.doReturn(Optional.of(creditCardToReturn)).when(repository).findById(Mockito.anyLong());

        CreditCard creditCardReturn = service.findById(1L);

        Assertions.assertThat(creditCardReturn).isNotNull();
        Assertions.assertThat(creditCardReturn.getId()).isEqualTo(creditCardToReturn.getId());

        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void whenFindCreditCardAndRepositoryException() {
        Mockito.doThrow(new RuntimeException()).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).findById(Mockito.anyLong());
    }
}
