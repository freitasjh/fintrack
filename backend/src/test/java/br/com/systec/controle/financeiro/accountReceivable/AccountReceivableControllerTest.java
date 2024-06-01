package br.com.systec.controle.financeiro.accountReceivable;

import br.com.systec.controle.financeiro.accountReceivable.fake.AccountReceivableFake;
import br.com.systec.controle.financeiro.financial.accountReceivable.filter.AccountReceivableFilterVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.controle.financeiro.financial.accountReceivable.service.AccountReceivableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountReceivableControllerTest {

    private static final String ENDPOINT = "/v1/accountReceivable";
    @MockBean
    private AccountReceivableService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("admin")
    void whenFindAccountReceivableById() throws Exception {
        AccountReceivable accountReceivable = AccountReceivableFake.fake();
        accountReceivable.setId(1L);

        Mockito.doReturn(accountReceivable).when(service).findAccountReceivableById(1L);

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    @WithMockUser("admin")
    void whenSaveAccountReceivableById() throws Exception {
        Page<AccountReceivable> pageOfAccountReceivable = new PageImpl<>(List.of(AccountReceivableFake.fake()));

        Mockito.doReturn(pageOfAccountReceivable).when(service).findByFilter(Mockito.any(AccountReceivableFilterVO.class));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/filter")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findByFilter(Mockito.any(AccountReceivableFilterVO.class));
    }
}
