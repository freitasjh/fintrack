package br.com.systec.fintrack.financial.creditCard.invoice.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditCard.model.CreditCard;
import br.com.systec.fintrack.financial.creditCard.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.financial.creditCard.invoice.model.InvoiceStatusType;
import br.com.systec.fintrack.financial.creditCard.invoice.repository.CreditCardInvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CreditCardInvoiceService {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoiceService.class);
    @Autowired
    private CreditCardInvoiceRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCardInvoice findByDateIfNotExistCreate(CreditCard creditCard) throws BaseException {
        CreditCardInvoice creditCardInvoice = repository.findByDateClose(LocalDate.now()).orElse(null);

        if (creditCardInvoice == null) {
            return create(creditCard);
        }

        return creditCardInvoice;
    }

    private CreditCardInvoice create(CreditCard creditCard) throws BaseException {
        try {
            CreditCardInvoice creditCardInvoice = new CreditCardInvoice();
            creditCardInvoice.setCreditCard(creditCard);
            creditCardInvoice.setDateClose(LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getClosingDate())));
            creditCardInvoice.setDueDate(LocalDate.now().withDayOfMonth(Integer.parseInt(creditCard.getDueDay())).plusMonths(1));
            creditCardInvoice.setTenantId(TenantContext.getTenant());
            creditCardInvoice.setStatusType(InvoiceStatusType.OPEN);

            return repository.save(creditCardInvoice);
        } catch (Exception e) {
            log.error("Erro ao tentar salvar a fatura", e);
            throw new BaseException(e.getMessage(), e);
        }
    }
}
