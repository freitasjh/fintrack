package br.com.systec.fintrack.category.api.v1.converter;

import br.com.systec.fintrack.category.api.v1.dto.CategoryDTO;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.category.model.CategoryType;
import org.springframework.data.domain.Page;

import java.util.List;

public final class CategoryConverter {
    private CategoryConverter(){

    }

    public static CategoryDTO toDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setSpendingLimit(category.getSpendingLimit());
        categoryDTO.setCategoryType(category.getCategoryType().getCode());

        return categoryDTO;
    }

    public static List<CategoryDTO> toListDTO(List<Category> listOfCategory){
        return listOfCategory.stream().map(CategoryConverter::toDTO).toList();
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setDescription(categoryDTO.getDescription());
        category.setObservation(categoryDTO.getObservation());
        category.setSpendingLimit(categoryDTO.getSpendingLimit());
        category.setCategoryType((CategoryType.valueOfCode(categoryDTO.getCategoryType())));

        return category;
    }

    public static Page<CategoryDTO> toPageDTO(Page<Category> pageOfCategory){
        return pageOfCategory.map(CategoryConverter::toDTO);
    }
}
