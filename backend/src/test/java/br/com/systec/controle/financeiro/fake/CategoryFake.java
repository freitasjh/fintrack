package br.com.systec.controle.financeiro.fake;

import br.com.systec.controle.financeiro.administrator.category.model.Category;

public class CategoryFake {

    public static Category fakeCategory(){
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Alimentação");

        return category;
    }
}
