package br.com.systec.fintrack.creditcard.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.creditcard.commons.CreditCardTransactionType;
import br.com.systec.fintrack.creditcard.filter.CreditCardFilterVO;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CreditCardService {
    CreditCard save(CreditCard creditCard) throws BaseException;
    CreditCard update(CreditCard creditCard, Long id) throws BaseException;
    List<CreditCard> findAll() throws BaseException;
    Page<CreditCard> findByFilter(CreditCardFilterVO filterVO) throws BaseException;
    CreditCard findById(Long id) throws BaseException;
    void updateAvailableLimitCreditCard(double amount, Long id, CreditCardTransactionType transactionType) throws BaseException;
}
