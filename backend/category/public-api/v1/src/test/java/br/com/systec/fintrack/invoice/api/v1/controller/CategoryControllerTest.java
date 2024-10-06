package br.com.systec.fintrack.invoice.api.v1.controller;

import br.com.systec.fintrack.invoice.api.v1.fake.CategoryFake;
import br.com.systec.fintrack.category.api.v1.controller.CategoryController;
import br.com.systec.fintrack.category.exception.CategoryNotFoundException;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.category.service.CategoryService;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.ControllerExceptionHandler;
import br.com.systec.fintrack.config.I18nTranslate;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {
    private static final String ENDPOINT = RestPath.V1+"/categories";
    @Mock
    private CategoryService service;
    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private CategoryController controller;
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

    //Todo verificar para arrumar erro de mensagem null na resposta do mock
    @Test
    void whenFindByIdObjectNotFoundException() throws Exception{
        Mockito.doThrow(new CategoryNotFoundException()).when(service).findById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT+"/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
