package br.com.systec.fintrack.creditcard.transaction.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardInstallment;

import java.time.LocalDate;
import java.util.List;

public interface CreditCardInstallmentService {

    void registerPaymentInstallmentsForInvoiceId(Long invoiceId, LocalDate datePay) throws BaseException;

    void bindInstallmentsToInvoiceIdCreditCard(Long creditCardInvoiceId, Long creditCardId, LocalDate dueDate) throws BaseException;

    List<CreditCardInstallment> findInstallmentByCreditCardInvoiceId(Long creditCardInvoiceId) throws BaseException;
}
