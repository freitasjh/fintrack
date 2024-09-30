package br.com.fintrack.category.fake;

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
}
