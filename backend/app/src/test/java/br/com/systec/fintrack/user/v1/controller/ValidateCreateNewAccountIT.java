package br.com.systec.fintrack.user.v1.controller;

import br.com.systec.fintrack.AbstractIT;
import br.com.systec.fintrack.integration.util.EndpointsContastsV1Test;
import br.com.systec.fintrack.integration.util.UserUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ValidateCreateNewAccountIT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(ValidateCreateNewAccountIT.class);
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void validateCreateBankAccountDefaultWhenCreateNewAccount() throws Exception {
        log.info("@@@@@@@@@ Validando se foi criado a conta default depois de criar a nova conta");

        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsContastsV1Test.BANK_ACCOUNT +"/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer "+ UserUtil.tokenAccess))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    @Order(2)
    void validateCreateCategoryWhenCreateNewAccount() throws Exception {
        log.info("@@@@@@@@ Validando se foi criado as categorias ao criar nova conta");

        mockMvc.perform(MockMvcRequestBuilders.get(EndpointsContastsV1Test.CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer "+ UserUtil.tokenAccess))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
