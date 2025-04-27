package br.com.systec.fintrack.budget.planning.api.filter;

import br.com.systec.fintrack.commons.filter.FilterParamVO;

import java.io.Serial;

public class BudgetPlanningFilterVO extends FilterParamVO {

    @Serial
    private static final long serialVersionUID = -834618539388512962L;
    private String yearMonthPlaning;


    public String getYearMonthPlaning() {
        return yearMonthPlaning;
    }

    public void setYearMonthPlaning(String yearMonthPlaning) {
        this.yearMonthPlaning = yearMonthPlaning;
    }
}
