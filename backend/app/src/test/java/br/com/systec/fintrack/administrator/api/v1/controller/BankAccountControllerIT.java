package br.com.systec.fintrack.administrator.api.v1.controller;

import br.com.systec.fintrack.AbstractIT;
import br.com.systec.fintrack.JsonUtil;
import br.com.systec.fintrack.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.fintrack.fake.BankAccountFake;
import br.com.systec.fintrack.financial.accountReceivable.service.AccountReceivableService;
import br.com.systec.fintrack.integration.util.EndpointsContastsV1Test;
import br.com.systec.fintrack.integration.util.UserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountControllerIT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(BankAccountControllerIT.class);
    private static Long bankAccountId;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountReceivableService accountReceivableService;

    @Test
    @Order(1)
    void whenSaveNewBakAccount() throws Exception {
        log.info("@@@@@@@ Salvando nova conta bancaria");
        BankAccountInputDTO bankAccountToSave = BankAccountFake.fakeInputDTO();
        bankAccountToSave.setId(null);
        bankAccountToSave.setInitialValue(1000.0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(EndpointsContastsV1Test.BANK_ACCOUNT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ UserUtil.tokenAccess)
                        .content(JsonUtil.converteObjetoParaString(bankAccountToSave)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        BankAccountInputDTO bankAccountSaved = (BankAccountInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), BankAccountInputDTO.class);
        bankAccountId = bankAccountSaved.getId();

        Assertions.assertThat(bankAccountSaved).isNotNull();
        Assertions.assertThat(bankAccountSaved.getId()).isNotNull();
        Assertions.assertThat(bankAccountSaved.getDescription()).isEqualTo(bankAccountToSave.getDescription());
        Assertions.assertThat(bankAccountSaved.getBankId()).isEqualTo(bankAccountToSave.getBankId());
        Thread.sleep(5000);
    }

    @Test
    @Order(2)
    void validateIfNewBankAccountRegisterAmountInitial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(EndpointsContastsV1Test.RECEIVER+"/filter?accountId="+bankAccountId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ UserUtil.tokenAccess))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].amount").value(1000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(3)
    void whenFindBankAccountById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(EndpointsContastsV1Test.BANK_ACCOUNT+"/"+bankAccountId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ UserUtil.tokenAccess))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bankAccountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000.0))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(4)
    void whenFindBankAccountByFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(EndpointsContastsV1Test.BANK_ACCOUNT+"/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ UserUtil.tokenAccess))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(2))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
