package br.com.systec.clinic.dashboard.api.v1.controller;

import br.com.systec.clinic.dashboard.api.v1.dto.AccountPaymentDashboardDTO;
import br.com.systec.clinic.dashboard.api.v1.dto.CategoryExpenseDTO;
import br.com.systec.clinic.dashboard.api.v1.dto.CreditCardInvoiceAmountOpenDTO;
import br.com.systec.clinic.dashboard.api.v1.mapper.AccountPaymentDashboardMapper;
import br.com.systec.clinic.dashboard.api.v1.mapper.CategoryExpenseMapper;
import br.com.systec.clinic.dashboard.service.DashboardService;
import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestPath.V1+"/dashboards")
@Tag(name = "Dashboard")
@SecurityRequirement(name = "Authorization")
public class DashboardController extends AbstractController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "/current-account-balance", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Double> findCurrentAccountBalance() {
        Double currentAccountBalance = dashboardService.findCurrentAccountBalance();

        return buildSuccessResponse(currentAccountBalance);
    }

    @GetMapping(value = "/monthly-expenses")
    public ResponseEntity<Double> findMonthlyExpenses() {
        Double totalExpense = dashboardService.findMonthlyExpenses();;
        return buildSuccessResponse(totalExpense);
    }

    @GetMapping(value = "/last-payment")
    public ResponseEntity<List<AccountPaymentDashboardDTO>> listOfLastTenPayment() {
        List<AccountPayment> listOfPayment = dashboardService.lastPayment();
        return buildSuccessResponse(AccountPaymentDashboardMapper.toListDTO(listOfPayment));
    }

    @GetMapping(value = "/category-expense")
    public ResponseEntity<List<CategoryExpenseDTO>> findExpenseByCategory() {
        List<CategoryExpenseVO> listOfCategoryExpense = dashboardService.findExpenseByCategory();

        return buildSuccessResponse(CategoryExpenseMapper.toListDTO(listOfCategoryExpense));
    }

    @GetMapping(value = "/payment-not-processed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> findTotalPaymentNotProcessed() {
        return buildSuccessResponse(dashboardService.findTotalPaymentNotProcessed());
    }

    @GetMapping(value = "/payment-open", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountPaymentDashboardDTO>> findAccountPaymentOpen() {
        List<AccountPayment> listOfAccountPayment = dashboardService.findAccountPaymentOpen();
        List<AccountPaymentDashboardDTO> listOfAccountPaymentDTO = AccountPaymentDashboardMapper.toListDTO(listOfAccountPayment);

        return buildSuccessResponse(listOfAccountPaymentDTO);
    }

    @GetMapping(value = "/invoice-amount-open", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditCardInvoiceAmountOpenDTO> findInvoiceAmountOpen() {
        Double totalInvoiceAmountOpen = dashboardService.findAmountInvoiceOpen();

        return buildSuccessResponse(new CreditCardInvoiceAmountOpenDTO(totalInvoiceAmountOpen));
    }
}
