package br.com.systec.fintrack.category.filter;

import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.category.model.CategoryType;
import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class FilterCategoryVO extends PageParamSearchVO<Category> {
    private static final String SORT_NAME = "description";
    private CategoryType categoryType;


    @Override
    public Pageable getPageable() {
        return PageRequest.of(page, limit, createSort(Sort.Direction.ASC, SORT_NAME));
    }

    @Override
    public Specification<Category> getSpecification() {
        return CategorySpecification.filterByKeywordAndTenant(search, categoryType);
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
