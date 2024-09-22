package br.com.systec.controle.financeiro.integration;

import br.com.systec.controle.financeiro.AbstractIT;
import br.com.systec.controle.financeiro.administrator.api.v1.controller.BankAccountControllerIT;
import br.com.systec.controle.financeiro.login.api.v1.LoginControllerIT;
import br.com.systec.controle.financeiro.user.v1.controller.UserControllerIT;
import br.com.systec.controle.financeiro.user.v1.controller.ValidateCreateNewAccountIT;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Suite
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses({UserControllerIT.class, LoginControllerIT.class, ValidateCreateNewAccountIT.class, BankAccountControllerIT.class})
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class SuiteIntegrationIT {
}
