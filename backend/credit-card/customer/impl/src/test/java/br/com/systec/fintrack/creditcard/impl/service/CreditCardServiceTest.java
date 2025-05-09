package br.com.systec.fintrack.creditcard.impl.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;

import br.com.systec.fintrack.creditcard.commons.CreditCardTransactionType;
import br.com.systec.fintrack.creditcard.filter.CreditCardFilterVO;
import br.com.systec.fintrack.creditcard.impl.fake.CreditCardFake;
import br.com.systec.fintrack.creditcard.impl.repository.CreditCardRepository;
import br.com.systec.fintrack.creditcard.impl.repository.CreditCardRepositoryJPA;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.systec.fintrack.creditcard.commons.DateCreditCardUtils.generateDateCloseWithDateTransaction;
import static br.com.systec.fintrack.creditcard.commons.DateCreditCardUtils.generateDueDateWithDateTransaction;

@ExtendWith(SpringExtension.class)
public class CreditCardServiceTest {

    @Mock
    private CreditCardRepositoryJPA repositoryJPA;
    @Mock
    private CreditCardRepository repository;
    @Mock
    private RabbitTemplate template;
    @InjectMocks
    private CreditCardServiceImpl service = Mockito.spy(new CreditCardServiceImpl());

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
    void whenUpdateCreditCard() {
        CreditCard creditCardToReturn = CreditCardFake.toFake();

        Mockito.doReturn(Optional.of(creditCardToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(creditCardToReturn).when(repository).update(Mockito.any(CreditCard.class));

        CreditCard creditCardUpdated = service.update(CreditCardFake.toFake(), 1L);

        Assertions.assertThat(creditCardUpdated).isNotNull();
        Assertions.assertThat(creditCardUpdated.getId()).isEqualTo(creditCardToReturn.getId());

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(repository).update(Mockito.any(CreditCard.class));

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

    @Test
    void whenUpdateLimitCreditCardExpense() {
        CreditCard creditCard = CreditCardFake.toFake();
        creditCard.setAvailableLimit(1000.0);

        Mockito.doReturn(Optional.of(creditCard)).when(repository).findById(Mockito.anyLong());

        service.updateAvailableLimitCreditCard(100.0, 1L, CreditCardTransactionType.EXPENSE);

        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        Mockito.verify(repository).updateCreditCardAvailableLimit(captor.capture(), Mockito.anyLong());
        Double newLimitAvailableCreditCard = captor.getValue();

        Assertions.assertThat(newLimitAvailableCreditCard).isEqualTo(900.0);
    }

    @Test
    void whenUpdateLimitCreditCardPayment() {
        CreditCard creditCard = CreditCardFake.toFake();
        creditCard.setAvailableLimit(1000.0);

        Mockito.doReturn(Optional.of(creditCard)).when(repository).findById(Mockito.anyLong());

        service.updateAvailableLimitCreditCard(100.0, 1L, CreditCardTransactionType.PAYMENT);

        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        Mockito.verify(repository).updateCreditCardAvailableLimit(captor.capture(), Mockito.anyLong());
        Double newLimitAvailableCreditCard = captor.getValue();

        Assertions.assertThat(newLimitAvailableCreditCard).isEqualTo(1100.0);
    }

    @Test
    void withDateCloseGenerate() {
        CreditCard creditCard = new CreditCard();
        creditCard.setClosingDate("05");
        creditCard.setDueDay("10");

        LocalDate dateTransaction = LocalDate.of(2025, 3, 28);

        LocalDate dateClose = generateDateCloseWithDateTransaction(creditCard, dateTransaction);
        System.out.println("Close Date: " + dateClose);

        LocalDate dueDate = generateDueDateWithDateTransaction(creditCard, 1, dateTransaction);
        System.out.println("Due Date: " + dueDate);
    }

}
