package br.com.systec.fintrack.creditcard.transaction.impl.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.transaction.impl.repository.CreditCardInstallmentRepository;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardInstallment;
import br.com.systec.fintrack.creditcard.transaction.service.CreditCardInstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditCardInstallmentServiceImpl implements CreditCardInstallmentService {

    @Autowired
    private CreditCardInstallmentRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void registerPaymentInstallmentsForInvoiceId(Long invoiceId, LocalDate datePay) throws BaseException {
        repository.registerPaymentInstallmentsForInvoiceId(invoiceId, datePay);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void bindInstallmentsToInvoiceIdCreditCard(Long creditCardInvoiceId, Long creditCardId, LocalDate dueDate) throws BaseException {
        repository.bindInstallmentsToCreditCardInvoiceId(creditCardInvoiceId, creditCardId, dueDate);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInstallment> findInstallmentByCreditCardInvoiceId(Long creditCardInvoiceId) throws BaseException {
        return repository.findInstallmentByCreditCardInvoiceId(creditCardInvoiceId);
    }
}
