package br.com.systec.controle.financeiro.administrator.api.v1.controller;

import br.com.systec.controle.financeiro.AbstractIT;
import br.com.systec.controle.financeiro.JsonUtil;
import br.com.systec.controle.financeiro.administrator.bankAccount.api.v1.dto.BankAccountInputDTO;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.fake.BankAccountFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountControllerIT extends AbstractIT {
    private static Logger log = LoggerFactory.getLogger(BankAccountControllerIT.class);
    private static final String ENDPOINT = "/v1/bankAccounts";
    private static Long bankAccountId;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountReceivableService accountReceivableService;

    @BeforeAll
    static void init(){
        TenantContext.add(1L);
    }

    @Test
    @WithMockUser
    @Order(1)
    void whenSaveNewBankAccount() throws Exception {
        log.info("@@@@@@@ Salvando nova conta bancaria @@@@@@@");
        BankAccountInputDTO bankAccountInputDTO = BankAccountFake.fakeInputDTO();
        bankAccountInputDTO.setId(null);
        bankAccountInputDTO.setBankId(1L);
        bankAccountInputDTO.setInitialValue(1000.0);

        String contentJson = JsonUtil.converteObjetoParaString(bankAccountInputDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(contentJson))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        AccountReceivableFilterVO filterAccountReceivable = new AccountReceivableFilterVO();
        filterAccountReceivable.setAccountId(1L);

        log.info("@@@@@@@ Verificando se foi cadastrado a receita de valor inicial da nova conta bancaria");
        Page<AccountReceivable> pageAccountReceivable = accountReceivableService.findByFilter(filterAccountReceivable);

        Assertions.assertThat(pageAccountReceivable).isNotNull();
        Assertions.assertThat(pageAccountReceivable.getSize()).isNotZero();
        Assertions.assertThat(pageAccountReceivable.getContent()).isNotEmpty();
        Assertions.assertThat(pageAccountReceivable.getContent().get(0).getAmount()).isEqualTo(bankAccountInputDTO.getInitialValue());

        log.info("@@@@@@@@ Valor inicial da conta bancaria {}", pageAccountReceivable.getContent().get(0).getAmount());
    }

    @Test
    @WithMockUser
    @Order(2)
    void whenFindBankAccountId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser
    @Order(3)
    void whenFindBankAccountIdAndObjectNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg")
                        .value("Conta não encontrada"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
