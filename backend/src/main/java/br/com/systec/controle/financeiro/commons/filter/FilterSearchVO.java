package br.com.systec.controle.financeiro.commons.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public abstract class FilterSearchVO<T> {

    protected String search;
    protected int limit;
    protected int page;

    public FilterSearchVO() {
        this.search = "";
        this.limit = 30;
        this.page = 0;
    }

    public FilterSearchVO(int limit, int page, String filter) {
        this.limit = limit;
        this.page = page;
        this.search = filter;
    }

    public abstract Specification<T> getSpecification();

    public Pageable getPageable() {
        return PageRequest.of(page, limit);
    }

    public String getSearch() {
        return Optional.ofNullable(search).orElse("");
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public Sort createSort(Sort.Direction direction, String... args) {
        Sort sort = Sort.by(direction, args);

        return sort;
    }
}