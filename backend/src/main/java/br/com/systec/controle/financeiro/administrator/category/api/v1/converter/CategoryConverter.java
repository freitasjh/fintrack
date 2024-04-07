package br.com.systec.controle.financeiro.administrator.category.api.v1.converter;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.api.v1.dto.CategoryDTO;

import java.util.List;

public final class CategoryConverter {

    private static CategoryConverter instance;

    private CategoryConverter(){

    }

    public static synchronized CategoryConverter getInstance(){
        if(instance == null){
            instance = new CategoryConverter();
        }

        return instance;
    }

    public CategoryDTO toDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setDescription(category.getDescription());

        return categoryDTO;
    }

    public List<CategoryDTO> toListDTO(List<Category> listOfCategory){
        return listOfCategory.stream().map(this::toDTO).toList();
    }
}
