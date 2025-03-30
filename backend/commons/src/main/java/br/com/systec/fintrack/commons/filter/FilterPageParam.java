package br.com.systec.fintrack.commons.filter;

import java.util.HashMap;
import java.util.Map;

public class FilterPageParam {

    protected Map<String, Object> params;
    protected int pageSize;
    protected int page;
    protected String sortField;
    protected String sortOrder;

    public FilterPageParam() {
        this.pageSize = 30;
        this.page = 0;
    }

    public FilterPageParam(int pageSize, int page) {
        this.pageSize = pageSize;
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void addParam(String key, Object value) {
        if(params == null) {
            params = new HashMap<>();
        }
        this.params.putIfAbsent(key, value);
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
