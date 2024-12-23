package br.com.systec.fintrack.dashboard.impl.service;

import br.com.systec.fintrack.dashboard.service.DashboardService;
import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import br.com.systec.fintrack.financial.transaction.service.TransactionService;
import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DashboardServiceImpl implements DashboardService {
    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private AccountPaymentService accountPaymentService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CreditCardInvoiceService creditCardInvoiceService;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findCurrentAccountBalance() {
        return bankAccountService.findCurrentAccountBalance();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findMonthlyExpenses() {
        return accountPaymentService.findMonthlyExpenses();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountPayment> lastPayment() {
        return accountPaymentService.findLastTenPayment();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CategoryExpenseVO> findExpenseByCategory() {
        return transactionService.findExpenseByCategory();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findTotalPaymentNotProcessed() {
        Double totalPaymentNotProcessed = accountPaymentService.findTotalPaymentNotProcessed();
        return Objects.requireNonNullElse(totalPaymentNotProcessed, 0.0);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AccountPayment> findAccountPaymentOpen() {
        return accountPaymentService.findAccountPaymentOpen();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findAmountInvoiceOpen() throws BaseException {
        return creditCardInvoiceService.findAmountInvoiceOpen();
    }
}
