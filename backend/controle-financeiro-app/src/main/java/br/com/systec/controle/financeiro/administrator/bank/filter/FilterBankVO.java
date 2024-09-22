package br.com.systec.controle.financeiro.administrator.bank.filter;

import br.com.systec.controle.financeiro.administrator.bank.model.Bank;
import br.com.systec.controle.financeiro.commons.filter.PageParamSearchVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class FilterBankVO extends PageParamSearchVO<Bank> {

    private static final String SORT_NAME = "name";

    public FilterBankVO(){}

    public FilterBankVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<Bank> getSpecification() {
        return BankSpecification.getInstance().filterByKeyword(search);
    }

    @Override
    public Pageable getPageable() {
        return PageRequest.of(page, limit, createSort(Sort.Direction.ASC, SORT_NAME));
    }
}
