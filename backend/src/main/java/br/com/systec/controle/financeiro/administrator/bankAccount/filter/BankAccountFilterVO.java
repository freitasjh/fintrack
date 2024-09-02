package br.com.systec.controle.financeiro.administrator.bankAccount.filter;

import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.commons.filter.PageParamSearchVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class BankAccountFilterVO extends PageParamSearchVO<BankAccount> {
    private static final String SORT_NAME = "description";

    public BankAccountFilterVO() {
    }

    public BankAccountFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Pageable getPageable() {
        return PageRequest.of(page, limit, createSort(Sort.Direction.ASC, SORT_NAME));
    }

    @Override
    public Specification<BankAccount> getSpecification() {
        return BankAccountSpecification.filterByKeywordAndTenant(search);
    }
}
