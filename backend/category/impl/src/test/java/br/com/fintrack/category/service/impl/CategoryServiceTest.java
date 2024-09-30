package br.com.fintrack.category.service.impl;

import br.com.fintrack.category.fake.CategoryFake;
import br.com.systec.fintrack.category.exception.CategoryNotFoundException;
import br.com.systec.fintrack.category.filter.FilterCategoryVO;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.category.repository.CategoryRepository;
import br.com.systec.fintrack.category.repository.CategoryRepositoryJPA;
import br.com.systec.fintrack.category.service.impl.CategoryServiceImpl;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.config.I18nTranslate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryRepositoryJPA repositoryJPA;
    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;
    @InjectMocks
    private CategoryServiceImpl service;

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
    void whenFindCategoryAndNotFoundException() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findById(1L)).
                isInstanceOf(CategoryNotFoundException.class);

        Mockito.verify(repository).findById(1L);
    }

    @Test
    public void whenFindAllCategories() {
        List<Category> listOfCategory = List.of(CategoryFake.fakeCategory());
        TenantContext.add(1L);

        Mockito.when(repository.findAllByTenantId()).thenReturn(listOfCategory);

        List<Category> result = service.findAll();

        Assertions.assertThat(listOfCategory.size()).isEqualTo(result.size());
        Assertions.assertThat(listOfCategory.get(0).getId()).isEqualTo(result.get(0).getId());
        Assertions.assertThat(1L).isEqualTo(result.get(0).getTenantId());

        Mockito.verify(repository, Mockito.times(1)).findAllByTenantId();
    }

    @Test
    void whenSaveNewCategory() {
        Category categoryReturn = CategoryFake.fakeCategory();

        Mockito.when(repository.save(Mockito.any(Category.class))).thenReturn(categoryReturn);

        Category categorySaved = service.save(CategoryFake.fakeCategory());

        Assertions.assertThat(categorySaved).isNotNull();
        Assertions.assertThat(categorySaved.getId()).isNotNull();
        Assertions.assertThat(categorySaved.getId()).isEqualTo(categoryReturn.getId());
        Assertions.assertThat(categorySaved.getDescription()).isEqualTo(categoryReturn.getDescription());
        Assertions.assertThat(categorySaved.getObservation()).isEqualTo(categoryReturn.getObservation());
        Assertions.assertThat(categorySaved.getSpendingLimit()).isEqualTo(categoryReturn.getSpendingLimit());

        Mockito.verify(repository).save(Mockito.any(Category.class));
    }

    @Test
    void whenFindCategoryByFilterAndPageable(){
        Page<Category> listOfCategory = new PageImpl<>(List.of(CategoryFake.fakeCategory()));
        FilterCategoryVO filterCategoryVO = new FilterCategoryVO();

        Mockito.when(repositoryJPA.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(listOfCategory);

        Page<Category> listOfCategoryReturn = service.findByFilterAndPageable(filterCategoryVO);

        Assertions.assertThat(listOfCategoryReturn).isNotNull();
        Assertions.assertThat(listOfCategory.getSize()).isEqualTo(listOfCategoryReturn.getSize());
        Assertions.assertThat(listOfCategory.getContent().get(0).getId()).isEqualTo(listOfCategoryReturn.getContent().get(0).getId());

        Mockito.verify(repositoryJPA).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

}