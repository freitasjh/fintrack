package br.com.systec.fintrack.user.v1.controller;

import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.user.model.User;
import br.com.systec.fintrack.user.service.UserService;
import br.com.systec.fintrack.user.v1.JsonUtil;
import br.com.systec.fintrack.user.v1.dto.UserInputDTO;
import br.com.systec.fintrack.user.v1.fake.UserFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final String ENDPOINT = RestPath.V1 + "/users";
    @Mock
    private UserService service;
    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private UserController userController;
    @InjectMocks
    private I18nTranslate i18nTranslate;
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void whenFindUserById() throws Exception {
        User userReturn = UserFake.fakeUser();

        Mockito.doReturn(userReturn).when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(Mockito.anyLong());
    }

//    @Test
//    void whenFindUserByIdAndObjectNotFoundException() throws Exception {
//        Mockito.doThrow(new ObjectNotFoundException("Usuario não encontrado")).when(service).findById(Mockito.anyLong());
//
//        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/1")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().is(400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").isNotEmpty())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        Mockito.verify(service).findById(Mockito.anyLong());
//    }

    @Test
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

//    @Test
//    void whenCreateNewAccountAndLoginFoundExceptionTest() throws Exception {
//        UserInputDTO userInsertBody = UserFake.fakeUserInputDTO();
//        Mockito.doThrow(new LoginEmailValidationException()).when(service)
//                .save(Mockito.any(User.class));
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/newAccount")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(JsonUtil.converteObjetoParaString(userInsertBody)))
//                .andExpect(MockMvcResultMatchers.status().is(400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").isNotEmpty())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        Mockito.verify(service).save(Mockito.any(User.class));
//    }
}
