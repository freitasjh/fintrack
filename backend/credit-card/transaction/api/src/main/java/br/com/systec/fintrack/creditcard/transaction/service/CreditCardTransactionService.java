package br.com.systec.fintrack.creditcard.transaction.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionPageParam;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import org.springframework.data.domain.Page;

public interface CreditCardTransactionService {
    CreditCardTransaction save(CreditCardTransaction creditCardTransaction) throws BaseException;
    Page<CreditCardTransaction> findByFilter(CreditCardTransactionPageParam pageParam) throws BaseException;
}
