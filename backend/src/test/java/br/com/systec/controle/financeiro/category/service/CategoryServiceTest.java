package br.com.systec.controle.financeiro.category.service;

import br.com.systec.controle.financeiro.category.model.Category;
import br.com.systec.controle.financeiro.category.repository.CategoryRepository;
import br.com.systec.controle.financeiro.fake.CategoryFake;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    public CategoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
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
    public void testFindAll() {
        List<Category> listOfCategory = Arrays.asList(CategoryFake.fakeCategory());
        Mockito.when(repository.findAll()).thenReturn(listOfCategory);

        List<Category> result = service.findAll();

        Assertions.assertThat(listOfCategory.size()).isEqualTo(result.size());
        Assertions.assertThat(listOfCategory.get(0).getId()).isEqualTo(result.get(0).getId());

        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}