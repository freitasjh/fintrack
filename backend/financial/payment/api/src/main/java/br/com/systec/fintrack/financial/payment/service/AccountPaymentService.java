package br.com.systec.fintrack.financial.payment.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.filter.AccountPaymentPageParam;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface AccountPaymentService {
    AccountPayment save(AccountPayment accountPayment) throws BaseException;

    List<AccountPayment> listAllPayment() throws BaseException;

    void delete(Long id) throws BaseException;

    Page<AccountPayment> findPaymentByFilter(AccountPaymentPageParam pageParam) throws BaseException;

    Double findMonthlyExpenses() throws BaseException;

    List<AccountPayment> findLastTenPayment() throws BaseException;

    Double findTotalPaymentNotProcessed() throws BaseException;

    List<AccountPayment> findAccountPaymentOpen() throws BaseException;

    void registerPayment(Long paymentId, Date dateRegister) throws BaseException;

    List<AccountPayment> findAccountPaymentPending() throws BaseException;


}
