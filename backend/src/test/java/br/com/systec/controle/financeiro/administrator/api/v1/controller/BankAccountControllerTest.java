package br.com.systec.controle.financeiro.administrator.api.v1.controller;

import br.com.systec.controle.financeiro.JsonUtil;
import br.com.systec.controle.financeiro.administrator.bankAccount.api.v1.converter.BankAccountConverter;
import br.com.systec.controle.financeiro.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.administrator.bankAccount.service.BankAccountService;
import br.com.systec.controle.financeiro.fake.BankAccountFake;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {
    private static final String ENDPOINT = "/v1/bankAccounts";
    @MockBean
    private BankAccountService service;
    @MockBean
    private BankAccountConverter converter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("admin")
    void whenFindBankAccountById() throws Exception {
        BankAccount bankAccount = BankAccountFake.fake();
        BankAccountInputDTO bankAccountInputDTOSave = BankAccountFake.fakeInputDTO();
        bankAccountInputDTOSave.setId(1L);

        Mockito.doReturn(bankAccount).when(service).findById(1L);

        Mockito.doReturn(BankAccountFake.fakeInputDTO()).when(converter).convertTOInputDTO(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(1L);
        Mockito.verify(converter).convertTOInputDTO(Mockito.any(BankAccount.class));
    }

    @Test
    @WithMockUser
    void whenSaveBankAccount() throws Exception {
        BankAccount bankAccountReturn = BankAccountFake.fake();
        bankAccountReturn.setId(1L);

        BankAccount bankAccountSave = BankAccountFake.fake();
        bankAccountSave.setId(null);

        BankAccountInputDTO bankAccountInputDTOSave = BankAccountFake.fakeInputDTO();
        bankAccountInputDTOSave.setId(null);

        Mockito.doReturn(bankAccountReturn).when(service).save(bankAccountSave);

        Mockito.doReturn(bankAccountSave).when(converter).convertToModel(Mockito.any());

        Mockito.doReturn(BankAccountFake.fakeInputDTO()).when(converter).convertTOInputDTO(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(bankAccountInputDTOSave)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).save(bankAccountSave);
        Mockito.verify(converter).convertToModel(Mockito.any());
        Mockito.verify(converter).convertTOInputDTO(Mockito.any());
    }

    @Test
    @WithMockUser
    void whenSaveBankAccountValidationException() throws Exception{
        BankAccountInputDTO bankAccountInputDTO = BankAccountFake.fakeInputDTO();
        bankAccountInputDTO.setDescription("");

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(bankAccountInputDTO)))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser
    void whenUpdateAccount() throws Exception{
        BankAccountInputDTO bankAccountInputDTO = BankAccountFake.fakeInputDTO();
        BankAccount bankAccountToReturn = BankAccountFake.fake();
        BankAccount bankAccountToSave = BankAccountFake.fake();
        bankAccountToSave.setId(1L);

        Mockito.when(converter.convertToModel(Mockito.any())).thenReturn(BankAccountFake.fake());
        Mockito.when(service.update(Mockito.any())).thenReturn(bankAccountToReturn);
        Mockito.when(converter.convertTOInputDTO(bankAccountToReturn)).thenReturn(bankAccountInputDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(bankAccountInputDTO)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).update(Mockito.any());
        Mockito.verify(converter).convertTOInputDTO(bankAccountToReturn);
        Mockito.verify(converter).convertToModel(Mockito.any());
    }
}
