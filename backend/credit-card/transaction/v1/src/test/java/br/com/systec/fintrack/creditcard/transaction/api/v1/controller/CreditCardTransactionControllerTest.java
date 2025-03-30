package br.com.systec.fintrack.creditcard.transaction.api.v1.controller;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.ControllerExceptionHandler;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.creditcard.transaction.api.v1.JsonUtil;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionInputDTO;
import br.com.systec.fintrack.creditcard.transaction.api.v1.fake.CreditCardTransactionFake;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import br.com.systec.fintrack.creditcard.transaction.service.CreditCardTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootTest
@SpringBootConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditCardTransactionControllerTest {
    private static final String ENDPOINT = "/v1/credit-cards/transactions";

    @Spy
    private CreditCardTransactionService service;
    @InjectMocks
    private CreditCardTransactionController controller;

    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        TenantContext.add(1L);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())

                .build();
    }

    @Test
    @Order(1)
    void whenSaveTransaction() throws Exception{
        CreditCardTransactionInputDTO transactionToSave = CreditCardTransactionFake.toFakeInputDTO();
        transactionToSave.setId(null);

        CreditCardTransaction transactionToReturn = CreditCardTransactionFake.toFake();

        Mockito.doReturn(transactionToReturn).when(service).save(Mockito.any(CreditCardTransaction.class));

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(transactionToSave)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).save(Mockito.any(CreditCardTransaction.class));
    }


    @Test
    @Order(2)
    void whenSaveTransactionWithValidException() throws Exception {
        CreditCardTransactionInputDTO transactionToSave = CreditCardTransactionFake.toFakeInputDTO();
        transactionToSave.setId(null);
        transactionToSave.setDescription(null);
        transactionToSave.setCreditCardId(null);

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(transactionToSave)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

//    @Test
//    @Order(3)
//    void whenFindByFilter() throws Exception {
//        Page<CreditCardTransaction> pageToReturn = new PageImpl<>(List.of(CreditCardTransactionFake.toFake()),
//                PageRequest.of(0, 1, Sort.by("id")), 1);
//
//        Mockito.doReturn(pageToReturn).when(service).findByFilter(Mockito.any(CreditCardTransactionPageParam.class));
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/filter")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().is(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(1))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }
}