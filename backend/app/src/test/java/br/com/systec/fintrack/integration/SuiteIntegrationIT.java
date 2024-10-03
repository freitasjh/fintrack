package br.com.systec.fintrack.integration;

import br.com.systec.fintrack.integration.bankaccount.v1.BankAccountControllerIT;
import br.com.systec.fintrack.login.api.v1.LoginControllerIT;
import br.com.systec.fintrack.user.v1.controller.UserControllerIT;
import br.com.systec.fintrack.user.v1.controller.ValidateCreateNewAccountIT;
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
