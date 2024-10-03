package br.com.systec.fintrack.invoice.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;

public interface CreditCardInvoiceService {
    CreditCardInvoice findByDateIfNotExistCreate(CreditCard creditCard) throws BaseException;
}
