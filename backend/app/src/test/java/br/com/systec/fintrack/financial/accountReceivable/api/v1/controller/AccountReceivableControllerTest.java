package br.com.systec.fintrack.financial.accountReceivable.api.v1.controller;

import br.com.systec.fintrack.JsonUtil;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.financial.accountReceivable.api.v1.dto.AccountReceivableInputDTO;
import br.com.systec.fintrack.financial.accountReceivable.exceptions.AccountReceivableNotFoundException;
import br.com.systec.fintrack.financial.accountReceivable.fake.AccountReceivableFake;
import br.com.systec.fintrack.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.fintrack.financial.accountReceivable.service.AccountReceivableService;
import br.com.systec.fintrack.integration.util.EndpointsContastsV1Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountReceivableControllerTest {

    @MockBean
    private AccountReceivableService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeTestClass
    public void init(){
        TenantContext.add(1L);
    }

    @AfterTestClass
    public void close() {
        TenantContext.clear();
    }

    @Test
    @WithMockUser
    void whenSaveAccountReceivable() throws Exception {
        AccountReceivableInputDTO accountReceivableToSave = AccountReceivableFake.toInputDTO();

        AccountReceivable accountReceivableAfterSave = AccountReceivableFake.toFake();
        accountReceivableAfterSave.setId(1L);

        Mockito.doReturn(accountReceivableAfterSave).when(service)
                .save(Mockito.any(AccountReceivable.class));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(EndpointsContastsV1Test.RECEIVER)
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
    @WithMockUser
    void whenSaveAccountReceivableValidationException() throws Exception{
        AccountReceivableInputDTO accountReceivableToSave = AccountReceivableFake.toInputDTO();
        accountReceivableToSave.setDescription(null);
        accountReceivableToSave.setBankAccountId(null);

        mockMvc.perform(MockMvcRequestBuilders.post(EndpointsContastsV1Test.RECEIVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(accountReceivableToSave)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser
    void whenFindAccountReceivable() throws Exception{
        AccountReceivable accountReceivableToReturn = AccountReceivableFake.toFake();
        accountReceivableToReturn.setId(1L);

        Mockito.doReturn(accountReceivableToReturn).when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsContastsV1Test.RECEIVER+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(accountReceivableToReturn.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(Mockito.anyLong());
    }

    @Test
    @WithMockUser
    void whenFindAccountReceivableObjectNotFoundException() throws Exception{
        AccountReceivable accountReceivableToReturn = AccountReceivableFake.toFake();
        accountReceivableToReturn.setId(1L);

        Mockito.doThrow(new AccountReceivableNotFoundException())
                .when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsContastsV1Test.RECEIVER+"/1")
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
    @WithMockUser
    void whenFindAllAccountReceivable() throws Exception {
        List<AccountReceivable> listAccountReceivableReturn = List.of(AccountReceivableFake.toFake());

        Mockito.doReturn(listAccountReceivableReturn).when(service).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsContastsV1Test.RECEIVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser
    void whenFindByFilter() throws Exception {
        Page<AccountReceivable> pageOfReturn = new PageImpl<>(List.of(AccountReceivableFake.toFake()));

        Mockito.doReturn(pageOfReturn).when(service).findByFilter(Mockito.any(AccountReceivableFilterVO.class));

        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsContastsV1Test.RECEIVER+"/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.size").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
