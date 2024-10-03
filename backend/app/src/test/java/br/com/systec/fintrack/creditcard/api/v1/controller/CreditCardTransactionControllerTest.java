package br.com.systec.fintrack.creditcard.api.v1.controller;

import br.com.systec.fintrack.JsonUtil;
import br.com.systec.fintrack.creditcard.api.v1.controller.fake.CreditCardTransactionFake;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionInputDTO;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionPageParam;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import br.com.systec.fintrack.creditcard.transaction.service.CreditCardTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CreditCardTransactionControllerTest {
    private static final String ENDPOINT = "/v1/credit-cards/transactions";

    @SpyBean
    private CreditCardTransactionService service;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
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
    @WithMockUser
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

    @Test
    @WithMockUser
    void whenFindByFilter() throws Exception {
        Page<CreditCardTransaction> pageToReturn = new PageImpl<>(List.of(CreditCardTransactionFake.toFake()));

        Mockito.doReturn(pageToReturn).when(service).findByFilter(Mockito.any(CreditCardTransactionPageParam.class));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.size").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}