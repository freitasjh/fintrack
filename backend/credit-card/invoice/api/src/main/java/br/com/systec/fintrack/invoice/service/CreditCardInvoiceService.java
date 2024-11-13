package br.com.systec.fintrack.invoice.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.vo.filter.CreditCardInvoiceFilterVO;

import java.time.LocalDate;
import java.util.List;

public interface CreditCardInvoiceService {

    CreditCardInvoice findByDateIfNotExistCreate(CreditCard creditCard) throws BaseException;

    List<CreditCardInvoiceVO> findByFilter(CreditCardInvoiceFilterVO filterVO) throws BaseException;

    List<CreditCardFutureInvoiceVO> findFutureInvoiceByFilter() throws BaseException;

    Double findAmountInvoiceOpen() throws BaseException;
}
