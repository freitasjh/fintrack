package br.com.systec.financial.payment.api.v1.controller;

import br.com.systec.financial.payment.api.v1.JsonUtil;
import br.com.systec.financial.payment.api.v1.fake.AccountPaymentFake;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.ControllerExceptionHandler;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import br.com.systec.fintrack.financial.payment.v1.controller.AccountPaymentController;
import br.com.systec.fintrack.financial.payment.v1.dto.AccountPaymentInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountPaymentControllerTest {
    private static final String ENDPOINT = "/v1/payments";
    @Spy
    private AccountPaymentService service;
    @InjectMocks
    private AccountPaymentController controller;
    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    @Order(1)
    void whenSaveNewAccountPayment() throws Exception {
        TenantContext.add(1L);

        AccountPaymentInputDTO accountPaymentInputDTOToSave = AccountPaymentFake.toInputDTO();
        accountPaymentInputDTOToSave.setId(null);

        AccountPayment accountPaymentToSave = AccountPaymentFake.toFake();
        accountPaymentToSave.setId(null);

        AccountPayment accountPaymentReturn = AccountPaymentFake.toFake();

        Mockito.doReturn(accountPaymentReturn).when(service)
                .save(Mockito.any(AccountPayment.class));

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(accountPaymentInputDTOToSave)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).save(Mockito.any(AccountPayment.class));
    }

    @Test
    @Order(2)
    void whenSaveNewPaymentAndReturnValidationException() throws Exception {
        TenantContext.add(1L);
        AccountPaymentInputDTO accountPaymentInputDTOToSave = AccountPaymentFake.toInputDTO();
        accountPaymentInputDTOToSave.setId(null);
        accountPaymentInputDTOToSave.setDescription(null);

        AccountPayment accountPaymentToSave = AccountPaymentFake.toFake();
        accountPaymentToSave.setId(null);

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(accountPaymentInputDTOToSave)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }
}
