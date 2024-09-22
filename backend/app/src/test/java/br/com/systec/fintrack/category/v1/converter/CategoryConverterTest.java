package br.com.systec.fintrack.category.v1.converter;

import br.com.systec.fintrack.administrator.category.api.v1.converter.CategoryConverter;
import br.com.systec.fintrack.administrator.category.model.Category;
import br.com.systec.fintrack.administrator.category.api.v1.dto.CategoryDTO;
import br.com.systec.fintrack.fake.CategoryFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CategoryConverterTest {

    @Test
    void converterCategoryToCategoryDTO(){
        Category category = CategoryFake.fakeCategory();

        CategoryDTO categoryDTO = CategoryConverter.toDTO(category);

        Assertions.assertThat(category.getId()).isEqualTo(categoryDTO.getId());
        Assertions.assertThat(category.getDescription()).isEqualTo(categoryDTO.getDescription());
    }

    @Test
    void converterListCategoryToListCategoryDTOTest(){
        List<Category> listOfCategory = Arrays.asList(CategoryFake.fakeCategory());

        List<CategoryDTO> listOfCategoryDTO = CategoryConverter.toListDTO(listOfCategory);

        Assertions.assertThat(listOfCategoryDTO).isNotEmpty();
        Assertions.assertThat(listOfCategoryDTO.size()).isEqualTo(listOfCategory.size());
    }

    @Test
    void whenConverteDtoToEntity() {
        CategoryDTO categoryDTO = CategoryFake.fakeCategoryDto();

        Category category = CategoryConverter.toEntity(categoryDTO);

        Assertions.assertThat(category).isNotNull();
        Assertions.assertThat(category.getDescription()).isEqualTo(categoryDTO.getDescription());
        Assertions.assertThat(category.getObservation()).isEqualTo(categoryDTO.getObservation());
        Assertions.assertThat(category.getSpendingLimit()).isEqualTo(categoryDTO.getSpendingLimit());

    }
}
