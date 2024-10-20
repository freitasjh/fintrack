package br.com.systec.fintrack.financial.recurringtransaction.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;
import com.fasterxml.jackson.databind.ser.Serializers;

public interface RecurringTransactionService {

    RecurringTransactionVO create(RecurringTransactionVO recurringTransaction) throws BaseException;
    RecurringTransactionVO update(RecurringTransactionVO recurringTransaction) throws BaseException;
    RecurringTransactionVO findByIdAndTenant(Long id) throws BaseException;
    void delete(Long transactionId) throws BaseException;
}
