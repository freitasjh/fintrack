package br.com.systec.fintrack.administrator.bank.filter;

import br.com.systec.fintrack.administrator.bank.model.Bank;
import org.springframework.data.jpa.domain.Specification;

public class BankSpecification {
    private static BankSpecification instance;

    private BankSpecification() {
    }

    public static BankSpecification getInstance() {
        if (instance == null) {
            instance = new BankSpecification();
        }

        return instance;
    }

    public Specification<Bank> filterByKeyword(String filter) {
        Specification<Bank> spec = null;

        if (filter != null && !filter.isEmpty()) {
            spec = Specification.where(generate(filter));
        }

        return spec;
    }

    private Specification<Bank> generate(String filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + filter + "%")
                );

    }
}
