package br.com.systec.fintrack.administrator.category.filter;

import br.com.systec.fintrack.administrator.category.enums.CategoryType;
import br.com.systec.fintrack.administrator.category.model.Category;
import br.com.systec.fintrack.commons.TenantContext;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    public static Specification<Category> filterByKeywordAndTenant(String filter, CategoryType categoryType){
        Specification<Category> spec = Specification.where(filterByTenant());

        if(filter != null && !filter.isEmpty()) {
            spec = spec.and(filterByKeyword(filter));
        }else if(categoryType != null) {
            spec = spec.and(filterByCategoryType(categoryType));
        }

        return spec;
    }

    private static Specification<Category> filterByTenant() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tenantId"), TenantContext.getTenant());
    }

    private static Specification<Category> filterByKeyword(String filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("description"), "%" + filter + "%")
                );
    }

    private static Specification<Category> filterByCategoryType(CategoryType categoryType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoryType"), categoryType);
    }
}
