package br.com.systec.controle.financeiro.administrator.category.filter;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.commons.TenantContext;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    public static Specification<Category> filterByKeywordAndTenant(String filter){
        Specification<Category> spec = Specification.where(filterByTenant());

        if(filter != null && !filter.isEmpty()) {
            spec = spec.and(filterByKeyword(filter));
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
}
