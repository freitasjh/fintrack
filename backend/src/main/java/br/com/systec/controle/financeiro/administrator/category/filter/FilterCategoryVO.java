package br.com.systec.controle.financeiro.administrator.category.filter;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.commons.filter.FilterSearchVO;
import org.springframework.data.jpa.domain.Specification;

public class FilterCategoryVO extends FilterSearchVO<Category> {

    @Override
    public Specification<Category> getSpecification() {
        return null;
    }
}
