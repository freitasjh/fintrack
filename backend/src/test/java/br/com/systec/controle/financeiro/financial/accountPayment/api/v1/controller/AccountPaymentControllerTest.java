package br.com.systec.controle.financeiro.financial.accountPayment.api.v1.controller;

import br.com.systec.controle.financeiro.JsonUtil;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountPayment.api.v1.dto.AccountPaymentInputDTO;
import br.com.systec.controle.financeiro.financial.accountPayment.fake.AccountPaymentFake;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.accountPayment.repository.AccountPaymentRepository;
import br.com.systec.controle.financeiro.financial.accountPayment.service.AccountPaymentService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountPaymentControllerTest {
    private static final String ENDPOINT = "/v1/payment";
    @MockBean
    private AccountPaymentService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void whenSaveNewAccountPayment() throws Exception {
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
}
