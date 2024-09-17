package br.com.systec.controle.financeiro.user.v1.controller;

import br.com.systec.controle.financeiro.AbstractIT;
import br.com.systec.controle.financeiro.JsonUtil;
import br.com.systec.controle.financeiro.administrator.user.v1.dto.UserInputDTO;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.integration.RabbitMQContainerIT;
import br.com.systec.controle.financeiro.integration.util.UserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIT extends AbstractIT implements RabbitMQContainerIT {
    private static final Logger log = LoggerFactory.getLogger(UserControllerIT.class);
    private static final String ENDPOINT = RestPath.V1 + "/users";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenSaveNewAccount() throws Exception {
        log.info("@@@@@@ Criando nova conta de usuario");
        UserInputDTO userToSave = UserUtil.generateUserToSave();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/newAccount")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(userToSave)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        UserInputDTO userSaved = (UserInputDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), UserInputDTO.class);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
    }
}
