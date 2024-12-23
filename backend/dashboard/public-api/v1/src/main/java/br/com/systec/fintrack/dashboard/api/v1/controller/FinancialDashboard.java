package br.com.systec.fintrack.dashboard.api.v1.controller;

import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/financial/dashboard")
@Tag(name = "Dashboard financeiro", description = "Cadastro de contas bancarias")
public class FinancialDashboard extends AbstractController {
}
