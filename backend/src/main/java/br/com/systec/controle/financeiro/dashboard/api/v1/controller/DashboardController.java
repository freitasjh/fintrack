package br.com.systec.controle.financeiro.dashboard.api.v1.controller;

import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return buildSuccessResponse(dashboardService.findMonthlyExpenses());
    }
}
