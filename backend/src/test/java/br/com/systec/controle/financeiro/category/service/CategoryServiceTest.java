package br.com.systec.controle.financeiro.category.service;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.repository.CategoryRepository;
import br.com.systec.controle.financeiro.administrator.category.service.CategoryService;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import br.com.systec.controle.financeiro.fake.CategoryFake;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    public CategoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindCategoriesById() {
        Long categoryId = 1L;
        Category expectedCategory = new Category();
        expectedCategory.setId(categoryId);

        Mockito.when(repository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        Category category = service.findById(categoryId);

        Assertions.assertThat(expectedCategory.getDescription()).isEqualTo(category.getDescription());
        Assertions.assertThat(expectedCategory.getObservation()).isEqualTo(category.getObservation());

        Mockito.verify(repository, Mockito.times(1)).findById(categoryId);
    }

    @Test
    void whenFindCategoryByIdObjectNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findById(1L)).
                isInstanceOf(ObjectNotFoundException.class)
                .as(I18nTranslate.toLocale("category.not.found"));
    }

    @Test
    public void whenFindAllCategories() {
        List<Category> listOfCategory = Arrays.asList(CategoryFake.fakeCategory());
        Mockito.when(repository.findAll()).thenReturn(listOfCategory);

        List<Category> result = service.findAll();

        Assertions.assertThat(listOfCategory.size()).isEqualTo(result.size());
        Assertions.assertThat(listOfCategory.get(0).getId()).isEqualTo(result.get(0).getId());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}