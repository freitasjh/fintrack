package br.com.systec.controle.financeiro.category.v1.controller;

import br.com.systec.controle.financeiro.JsonUtil;
import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.service.CategoryService;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.fake.CategoryFake;
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

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CategoryControllerTest {
    private static final String ENDPOINT = RestPath.V1+"/categories";
    @MockBean
    private CategoryService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void whenFindAllCategory() throws Exception {
        List<Category> listOfCategoryReturn = List.of(CategoryFake.fakeCategory());

        Mockito.when(service.findAll()).thenReturn(listOfCategoryReturn);

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser
    void whenFindById() throws Exception{
        Category categoryToReturn = CategoryFake.fakeCategory();

        Mockito.when(service.findById(1L)).thenReturn(categoryToReturn);

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser
    void whenFindByIdObjectNotFoundException() throws Exception{
        Mockito.when(service.findById(2L)).thenThrow(new ObjectNotFoundException("Categoria não encontrada"));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
