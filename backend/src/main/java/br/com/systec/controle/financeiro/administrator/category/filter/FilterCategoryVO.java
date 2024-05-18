package br.com.systec.controle.financeiro.administrator.category.filter;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.commons.filter.FilterSearchVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class FilterCategoryVO extends FilterSearchVO<Category> {
    private static final String SORT_NAME = "description";


    @Override
    public Pageable getPageable() {
        return PageRequest.of(page, limit, createSort(Sort.Direction.ASC, SORT_NAME));
    }

    @Override
    public Specification<Category> getSpecification() {
        return CategorySpecification.filterByKeywordAndTenant(search);
    }
}
