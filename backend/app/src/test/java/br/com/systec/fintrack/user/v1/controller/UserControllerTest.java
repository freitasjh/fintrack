package br.com.systec.fintrack.user.v1.controller;

import br.com.systec.fintrack.JsonUtil;
import br.com.systec.fintrack.administrator.user.exception.LoginEmailValidationException;
import br.com.systec.fintrack.administrator.user.model.User;
import br.com.systec.fintrack.administrator.user.service.UserService;
import br.com.systec.fintrack.administrator.user.v1.dto.UserInputDTO;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.fake.UserFake;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {
    private static final String ENDPOINT = RestPath.V1+"/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    @WithMockUser("admin")
    void whenFindUserById() throws Exception {
        User userReturn = UserFake.fakeUser();

        Mockito.doReturn(userReturn).when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(Mockito.anyLong());
    }

    @Test
    @WithMockUser("admin")
    void whenFindUserByIdAndObjectNotFoundException() throws Exception {
        Mockito.doThrow(new ObjectNotFoundException("Usuario não encontrado")).when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(Mockito.anyLong());
    }

    @Test
    @WithMockUser("admin")
    void whenSaveUserAndCreateNewAccount() throws Exception {
        User userCreated = UserFake.fakeUser();
        UserInputDTO userInsertBody = UserFake.fakeUserInputDTO();


        Mockito.when(service.save(Mockito.any(User.class))).thenReturn(userCreated);

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(userInsertBody)))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void whenCreateNewAccountAndLoginFoundExceptionTest() throws Exception {
        UserInputDTO userInsertBody = UserFake.fakeUserInputDTO();
        Mockito.doThrow(new LoginEmailValidationException()).when(service)
                .save(Mockito.any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT+"/newAccount")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.converteObjetoParaString(userInsertBody)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).save(Mockito.any(User.class));
    }
}
