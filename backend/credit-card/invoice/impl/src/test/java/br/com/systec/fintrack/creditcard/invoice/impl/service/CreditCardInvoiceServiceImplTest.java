package br.com.systec.fintrack.creditcard.invoice.impl.service;

import br.com.systec.fintrack.creditcard.invoice.impl.fake.CreditCardInvoiceFake;
import br.com.systec.fintrack.creditcard.invoice.impl.repository.CreditCardInvoiceRepository;
import br.com.systec.fintrack.creditcard.invoice.impl.service.CreditCardInvoiceServiceImpl;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@SpringBootConfiguration
public class CreditCardInvoiceServiceImplTest {

    @Mock
    private CreditCardInvoiceRepository repository;
    @InjectMocks
    private CreditCardInvoiceService service = Mockito.spy(new CreditCardInvoiceServiceImpl());

    @Test
    void whenFindByDataAndExistInvoice() throws Exception {
        CreditCardInvoice creditCardInvoice = CreditCardInvoiceFake.fake();
        CreditCard creditCardFake = new CreditCard();
        creditCardFake.setId(1L);

        Mockito.doReturn(Optional.of(creditCardInvoice)).when(repository).findByDateClose(Mockito.any(LocalDate.class), Mockito.anyLong());

        service.findByDateIfNotExistCreate(creditCardFake);
    }
}
