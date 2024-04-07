package br.com.systec.controle.financeiro.category.v1.converter;

import br.com.systec.controle.financeiro.administrator.category.api.v1.converter.CategoryConverter;
import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.api.v1.dto.CategoryDTO;
import br.com.systec.controle.financeiro.fake.CategoryFake;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CategoryConverterTest {

    @Test
    void converterCategoryToCategoryDTO(){
        Category category = CategoryFake.fakeCategory();

        CategoryDTO categoryDTO = CategoryConverter.getInstance().toDTO(category);

        Assertions.assertThat(category.getId()).isEqualTo(categoryDTO.getId());
        Assertions.assertThat(category.getDescription()).isEqualTo(categoryDTO.getDescription());
    }

    @Test
    void converterListCategoryToListCategoryDTOTest(){
        List<Category> listOfCategory = Arrays.asList(CategoryFake.fakeCategory());

        List<CategoryDTO> listOfCategoryDTO = CategoryConverter.getInstance().toListDTO(listOfCategory);

        Assertions.assertThat(listOfCategoryDTO).isNotEmpty();
        Assertions.assertThat(listOfCategoryDTO.size()).isEqualTo(listOfCategory.size());
    }
}
