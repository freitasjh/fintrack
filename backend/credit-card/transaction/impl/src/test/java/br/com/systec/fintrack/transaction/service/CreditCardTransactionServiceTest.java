package br.com.systec.fintrack.transaction.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.creditcard.commons.CreditCardTransactionType;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.service.CreditCardService;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionPageParam;
import br.com.systec.fintrack.creditcard.transaction.impl.repository.CreditCardTransactionRepository;
import br.com.systec.fintrack.creditcard.transaction.impl.repository.CreditCardTransactionRepositoryJPA;
import br.com.systec.fintrack.creditcard.transaction.impl.service.CreditCardTransactionServiceImpl;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import br.com.systec.fintrack.transaction.fake.CreditCardInvoiceFake;
import br.com.systec.fintrack.transaction.fake.CreditCardTransactionFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CreditCardTransactionServiceTest {
    private static final Logger log = LoggerFactory.getLogger(CreditCardTransactionServiceTest.class);

    @Mock
    private CreditCardInvoiceService invoiceService;
    @Spy
    private CreditCardService creditCardService;
    @Mock
    private CreditCardTransactionRepository repository;
    @Mock
    private CreditCardTransactionRepositoryJPA repositoryJPA;
    @InjectMocks
    private CreditCardTransactionServiceImpl service = Mockito.spy(new CreditCardTransactionServiceImpl());

    @Test
    void whenGenerateInstalmentOnSaveTransaction() {
        TenantContext.add(1L);
        CreditCardTransaction creditCardTransactionToSave = CreditCardTransactionFake.toFake();
        creditCardTransactionToSave.setInstallments(3);
        creditCardTransactionToSave.setDateTransaction(LocalDate.of(2025,1,1));
        CreditCardInvoice creditCardInvoiceToReturn = CreditCardInvoiceFake.toFake();
        CreditCard creditCardToReturn = CreditCardTransactionFake.toFakeCreditCard();

        Mockito.doReturn(creditCardToReturn).when(creditCardService).findById(Mockito.anyLong());
        Mockito.doReturn(creditCardInvoiceToReturn).when(invoiceService).findByDateTransactionIfNotExistCreate(Mockito.any(LocalDate.class), Mockito.any(CreditCard.class));
        Mockito.doNothing().when(creditCardService).updateAvailableLimitCreditCard(Mockito.anyDouble(), Mockito.anyLong(),
                Mockito.any(CreditCardTransactionType.class));

        Mockito.when(repository.save(Mockito.any(CreditCardTransaction.class)))
                .thenAnswer(invocation -> {
            CreditCardTransaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setId(1L);
            return savedTransaction;
        });

        CreditCardTransaction creditCardSaved = service.save(creditCardTransactionToSave);

        Assertions.assertThat(creditCardSaved).isNotNull();
        Assertions.assertThat(creditCardSaved.getListOfInstallment()).isNotNull();
        Assertions.assertThat(creditCardSaved.getListOfInstallment()).isNotEmpty();

        Assertions.assertThat(creditCardSaved.getListOfInstallment().size()).isEqualTo(creditCardTransactionToSave.getInstallments());
        log.info("Lista de parcelas {}", creditCardSaved.getListOfInstallment());

        Mockito.verify(creditCardService).findById(Mockito.anyLong());
        Mockito.verify(invoiceService, Mockito.times(3)).findByDateTransactionIfNotExistCreate(Mockito.any(LocalDate.class),Mockito.any(CreditCard.class));
        Mockito.verify(creditCardService).updateAvailableLimitCreditCard(Mockito.anyDouble(), Mockito.anyLong(), Mockito.any(CreditCardTransactionType.class));
    }

    @Test
    void whenFilterByFilter() {
        PaginatedList<CreditCardTransaction> pageOfCreditTransaction = new PaginatedList<>();
        pageOfCreditTransaction.addAll(List.of(CreditCardTransactionFake.toFake()));

        CreditCardTransactionPageParam pageParam = new CreditCardTransactionPageParam(30,0);

        Mockito.doReturn(pageOfCreditTransaction).when(repository).findByFilter(Mockito.any(CreditCardTransactionPageParam.class));

        PaginatedList<CreditCardTransaction> pageReturn = service.findByFilter(pageParam);

        Assertions.assertThat(pageReturn).isNotNull();
        Assertions.assertThat(pageReturn.getTotalResults()).isEqualTo(pageOfCreditTransaction.getTotalResults());

        Mockito.verify(repository).findByFilter(Mockito.any(CreditCardTransactionPageParam.class));
    }
}
