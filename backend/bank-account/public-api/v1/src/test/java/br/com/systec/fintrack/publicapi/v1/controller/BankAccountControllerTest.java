package br.com.systec.fintrack.publicapi.v1.controller;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.exception.ControllerExceptionHandler;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.publicapi.v1.JsonUtil;
import br.com.systec.fintrack.publicapi.v1.converter.BankAccountConverter;
import br.com.systec.fintrack.publicapi.v1.dto.BankAccountInputDTO;
import br.com.systec.fintrack.publicapi.v1.fake.BankAccountFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
public class BankAccountControllerTest {
    private static final String ENDPOINT = "/v1/bank-accounts";
    @Mock
    private BankAccountService service;
    @Mock
    private BankAccountConverter converter;
    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private BankAccountController controller;
    @InjectMocks
    private I18nTranslate i18nTranslate;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void whenFindBankAccountById() throws Exception {
        BankAccount bankAccount = BankAccountFake.fake();
        BankAccountInputDTO bankAccountInputDTOSave = BankAccountFake.fakeInputDTO();
        bankAccountInputDTOSave.setId(1L);

        Mockito.doReturn(bankAccount).when(service).findById(1L);

        Mockito.doReturn(BankAccountFake.fakeInputDTO()).when(converter).convertTOInputDTO(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findById(1L);
        Mockito.verify(converter).convertTOInputDTO(Mockito.any(BankAccount.class));
    }

    @Test
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
    void whenUpdateAccount() throws Exception {
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
