package br.com.systec.fintrack.invoice.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInstallmentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoicePaymentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.vo.filter.CreditCardInvoiceFilterParam;

import java.time.LocalDate;
import java.util.List;

public interface CreditCardInvoiceService {

    CreditCardInvoice findByDateIfNotExistCreate(CreditCard creditCard) throws BaseException;

    PaginatedList<CreditCardInvoiceResultVO> findByFilter(CreditCardInvoiceFilterParam filterVO) throws BaseException;

    List<CreditCardFutureInvoiceVO> findFutureInvoiceByFilter() throws BaseException;

    Double findAmountInvoiceOpen() throws BaseException;

    void paymentInvoice(CreditCardInvoicePaymentVO invoicePayment) throws BaseException;

    void validateAndMarkInvoiceAsPending(Long creditCardInvoiceId) throws BaseException;

    List<CreditCardInstallmentVO> findInstallmentByCreditCardInvoiceId(Long creditCardInvoiceId) throws BaseException;

    CreditCardInvoice findByDateTransactionIfNotExistCreate(LocalDate dateTransaction, CreditCard creditCard) throws BaseException;

}
