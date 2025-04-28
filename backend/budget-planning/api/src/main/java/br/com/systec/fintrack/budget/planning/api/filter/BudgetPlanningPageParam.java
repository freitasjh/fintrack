package br.com.systec.fintrack.budget.planning.api.filter;

import br.com.systec.fintrack.commons.filter.FilterPageParam;

public class BudgetPlanningPageParam extends FilterPageParam {

    private BudgetPlanningFilterVO filter;

    public BudgetPlanningPageParam(int pageSize, int page) {
        super(pageSize, page);
    }

    public BudgetPlanningFilterVO getFilter() {
        return filter;
    }

    public void setFilter(BudgetPlanningFilterVO filter) {
        this.filter = filter;
    }
}
