package br.com.systec.controle.financeiro.dashboard.api.v1.controller;

import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.dashboard.api.v1.dto.AccountPaymentDashboardDTO;
import br.com.systec.controle.financeiro.dashboard.api.v1.dto.CategoryExpenseDTO;
import br.com.systec.controle.financeiro.dashboard.api.v1.mapper.AccountPaymentDashboardMapper;
import br.com.systec.controle.financeiro.dashboard.api.v1.mapper.CategoryExpenseMapper;
import br.com.systec.controle.financeiro.dashboard.service.DashboardService;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.dto.AccountPaymentDTO;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.mapper.AccountPaymentMapper;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.transaction.vo.CategoryExpenseVO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping(RestPath.V1+"/dashboard")
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
}
