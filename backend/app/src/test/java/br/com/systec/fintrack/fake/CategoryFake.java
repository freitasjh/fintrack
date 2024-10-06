package br.com.systec.fintrack.fake;

import br.com.systec.fintrack.category.api.v1.dto.CategoryDTO;

public class CategoryFake {

    public static CategoryDTO fakeCategoryDto(){
        CategoryDTO category = new CategoryDTO();
        category.setId(1L);
        category.setDescription("Alimentação");
        category.setSpendingLimit(1000.0);

        return category;
    }
}
