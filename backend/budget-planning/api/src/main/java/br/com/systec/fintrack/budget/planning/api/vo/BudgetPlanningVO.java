package br.com.systec.fintrack.budget.planning.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BudgetPlanningVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7184027330404773440L;
    private Long id;
    private String description;
    private String mouthAndYearPlaning;
    private Double expectedValueExpense;
    private List<BudgetPlaningCategoryVO> listOfBudgetPlaningCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMouthAndYearPlaning() {
        return mouthAndYearPlaning;
    }

    public void setMouthAndYearPlaning(String mouthAndYearPlaning) {
        this.mouthAndYearPlaning = mouthAndYearPlaning;
    }

    public Double getExpectedValueExpense() {
        return expectedValueExpense;
    }

    public void setExpectedValueExpense(Double expectedValueExpense) {
        this.expectedValueExpense = expectedValueExpense;
    }

    public List<BudgetPlaningCategoryVO> getListOfBudgetPlaningCategory() {
        return listOfBudgetPlaningCategory;
    }

    public void setListOfBudgetPlaningCategory(List<BudgetPlaningCategoryVO> listOfBudgetPlaningCategory) {
        this.listOfBudgetPlaningCategory = listOfBudgetPlaningCategory;
    }
}
