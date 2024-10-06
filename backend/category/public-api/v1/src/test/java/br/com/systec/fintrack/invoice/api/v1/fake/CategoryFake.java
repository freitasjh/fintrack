package br.com.systec.fintrack.invoice.api.v1.fake;

import br.com.systec.fintrack.category.api.v1.dto.CategoryDTO;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.category.model.CategoryType;

public class CategoryFake {

    public static Category fakeCategory(){
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Alimentação");
        category.setSpendingLimit(1000.0);
        category.setTenantId(1L);
        category.setCategoryType(CategoryType.EXPENSE);

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
