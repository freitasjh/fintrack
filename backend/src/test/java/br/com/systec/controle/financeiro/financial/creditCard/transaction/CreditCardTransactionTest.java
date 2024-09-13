package br.com.systec.controle.financeiro.financial.creditCard.transaction;

import br.com.systec.controle.financeiro.creditCard.fake.CreditCardFake;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import br.com.systec.controle.financeiro.creditCard.service.CreditCardService;
import br.com.systec.controle.financeiro.financial.creditCard.commons.CreditCardTransactionType;
import br.com.systec.controle.financeiro.financial.creditCard.fake.CreditCardInvoiceFake;
import br.com.systec.controle.financeiro.financial.creditCard.fake.CreditCardTransactionFake;
import br.com.systec.controle.financeiro.financial.creditCard.invoice.model.CreditCardInvoice;
import br.com.systec.controle.financeiro.financial.creditCard.invoice.service.CreditCardInvoiceService;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.model.CreditCardTransaction;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.repository.CreditCardTransactionRepository;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.service.CreditCardTransactionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CreditCardTransactionTest {
    private static final Logger log = LoggerFactory.getLogger(CreditCardTransactionTest.class);

    @Mock
    private CreditCardInvoiceService invoiceService;
    @Mock
    private CreditCardService creditCardService;
    @Mock
    private CreditCardTransactionRepository repository;
    @InjectMocks
    private CreditCardTransactionService service;

    @Test
    void whenGenerateInstalmentOnSaveTransaction() {
        CreditCardTransaction creditCardTransactionToSave = CreditCardTransactionFake.toFake();
        CreditCardInvoice creditCardInvoiceToReturn = CreditCardInvoiceFake.toFake();
        CreditCard creditCardToReturn = CreditCardFake.toFake();

        Mockito.doReturn(creditCardToReturn).when(creditCardService).findById(Mockito.anyLong());
        Mockito.doReturn(creditCardInvoiceToReturn).when(invoiceService).findByDateIfNotExistCreate(Mockito.any(CreditCard.class));
        Mockito.doNothing().when(creditCardService).updateAvailableLimitCreditCard(Mockito.anyDouble(), Mockito.anyLong(), Mockito.any(CreditCardTransactionType.class));

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

        Assertions.assertThat(creditCardSaved.getListOfInstallment().get(0).getCreditCardInvoiceId()).isEqualTo(creditCardInvoiceToReturn.getId());

        log.info("Lista de parcelas {}", creditCardSaved.getListOfInstallment().get(0).toString());

        Mockito.verify(creditCardService).findById(Mockito.anyLong());
        Mockito.verify(invoiceService).findByDateIfNotExistCreate(Mockito.any(CreditCard.class));
        Mockito.verify(creditCardService).updateAvailableLimitCreditCard(Mockito.anyDouble(), Mockito.anyLong(), Mockito.any(CreditCardTransactionType.class));
    }
}
