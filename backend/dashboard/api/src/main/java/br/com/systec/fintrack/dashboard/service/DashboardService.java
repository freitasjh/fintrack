package br.com.systec.fintrack.dashboard.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;

import java.util.List;

public interface DashboardService {

    Double findCurrentAccountBalance() throws BaseException;

    Double findMonthlyExpenses() throws BaseException;

    List<AccountPayment> lastPayment() throws BaseException;

    List<CategoryExpenseVO> findExpenseByCategory() throws BaseException;

    Double findTotalPaymentNotProcessed() throws BaseException;

    List<AccountPayment> findAccountPaymentOpen() throws BaseException;

    Double findAmountInvoiceOpen() throws BaseException;
}
