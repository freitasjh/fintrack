package br.com.systec.controle.financeiro.login.api.v1;

import br.com.systec.controle.financeiro.AbstractIT;
import br.com.systec.controle.financeiro.JsonUtil;
import br.com.systec.controle.financeiro.administrator.user.v1.dto.UserInputDTO;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.integration.RabbitMQContainerIT;
import br.com.systec.controle.financeiro.integration.util.UserUtil;
import br.com.systec.controle.financeiro.login.api.v1.dto.LoginDTO;
import br.com.systec.controle.financeiro.login.api.v1.dto.LoginResponseDTO;
import br.com.systec.controle.financeiro.user.v1.controller.UserControllerIT;
import org.assertj.core.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.okhttp3.internal.Util;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerIT extends AbstractIT {
    private static final Logger log = LoggerFactory.getLogger(UserControllerIT.class);
    private static final String ENDPOINT = RestPath.V1 + "/login";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenLoginAccount() throws Exception {
        log.info("@@@@@@@ Fazendo o login do usuario cadastrado");
        UserInputDTO inputDTO = UserUtil.generateUserToSave();
        LoginDTO login = new LoginDTO();
        login.setUsername(inputDTO.getUsername());
        login.setPassword(inputDTO.getPassword());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(login)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        LoginResponseDTO response = (LoginResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), LoginResponseDTO.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getToken()).isNotNull();

        UserUtil.tokenAccess = response.getToken();
        log.info("@@@@@@@@@ Token gerado");

    }

}
