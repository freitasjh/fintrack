package br.com.systec.controle.financeiro.fake;

import br.com.systec.controle.financeiro.administrator.category.api.v1.dto.CategoryDTO;
import br.com.systec.controle.financeiro.administrator.category.model.Category;

public class CategoryFake {

    public static Category fakeCategory(){
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Alimentação");
        category.setSpendingLimit(1000.0);
        category.setTenantId(1L);

        return category;
    }

    public static CategoryDTO fakeCategoryDto(){
        CategoryDTO category = new CategoryDTO();
        category.setId(1L);
        category.setDescription("Alimentação");
        category.setSpendingLimit(1000.0);

        return category;
    }
}
