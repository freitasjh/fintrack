package br.com.systec.financial.received.api.v1.controller;

import br.com.systec.financial.received.api.v1.JsonUtil;
import br.com.systec.financial.received.api.v1.fake.AccountReceivableFake;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.ControllerExceptionHandler;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.financial.received.api.v1.controller.AccountReceivableController;
import br.com.systec.fintrack.financial.received.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableNotFoundException;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@SpringBootTest
@SpringBootConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountReceivableControllerTest {
    public static final String RECEIVER = RestPath.V1 + "/receivable";
    @Spy
    private AccountReceivableService service;
    @InjectMocks
    private AccountReceivableController controller;
    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        TenantContext.add(1L);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .setValidator(new LocalValidatorFactoryBean())

                .build();
    }

    @AfterTestClass
    public void close() {
        TenantContext.clear();
    }

    @Test
    void whenSaveAccountReceivable() throws Exception {
        AccountReceivableInputDTO accountReceivableToSave = AccountReceivableFake.toInputDTO();

        AccountReceivable accountReceivableAfterSave = AccountReceivableFake.toFake();
        accountReceivableAfterSave.setId(1L);

        Mockito.doReturn(accountReceivableAfterSave).when(service)
                .save(Mockito.any(AccountReceivable.class));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(RECEIVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(accountReceivableToSave)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        AccountReceivableInputDTO accountReceivableReturn = (AccountReceivableInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), AccountReceivableInputDTO.class);

        Assertions.assertThat(accountReceivableReturn).isNotNull();
        Assertions.assertThat(accountReceivableReturn.getId()).isNotNull();
        Assertions.assertThat(accountReceivableReturn.getBankAccountId()).isEqualTo(accountReceivableToSave.getBankAccountId());

        Mockito.verify(service).save(Mockito.any(AccountReceivable.class));
    }

    @Test
    void whenSaveAccountReceivableValidationException() throws Exception {
        AccountReceivableInputDTO accountReceivableToSave = AccountReceivableFake.toInputDTO();
        accountReceivableToSave.setDescription(null);
        accountReceivableToSave.setBankAccountId(null);

        mockMvc.perform(MockMvcRequestBuilders.post(RECEIVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(accountReceivableToSave)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void whenFindAccountReceivable() throws Exception {
        AccountReceivable accountReceivableToReturn = AccountReceivableFake.toFake();
        accountReceivableToReturn.setId(1L);

        Mockito.doReturn(accountReceivableToReturn).when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(RECEIVER + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(accountReceivableToReturn.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(Mockito.anyLong());
    }

    @Test
    void whenFindAccountReceivableObjectNotFoundException() throws Exception {
        AccountReceivable accountReceivableToReturn = AccountReceivableFake.toFake();
        accountReceivableToReturn.setId(1L);

        Mockito.doThrow(new AccountReceivableNotFoundException())
                .when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(RECEIVER + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg")
                        .value(I18nTranslate.toLocale("account.receivable.not.found")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(Mockito.anyLong());
    }

    @Test
    void whenFindAllAccountReceivable() throws Exception {
        List<AccountReceivable> listAccountReceivableReturn = List.of(AccountReceivableFake.toFake());

        Mockito.doReturn(listAccountReceivableReturn).when(service).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get(RECEIVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void whenFindByFilter() throws Exception {
        Page<AccountReceivable> pageOfReturn = new PageImpl<>(List.of(AccountReceivableFake.toFake()), PageRequest.of(0, 1, Sort.by("id")), 1);

        Mockito.doReturn(pageOfReturn).when(service).findByFilter(Mockito.any(AccountReceivableFilterVO.class));

        mockMvc.perform(MockMvcRequestBuilders.get(RECEIVER + "/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
