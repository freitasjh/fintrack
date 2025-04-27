package br.com.systec.fintrack.budget.planning.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class BudgetPlaningCategoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2610368118958209808L;
    private Long id;
    private Long categoryId;
    private String categoryDescription;
    private Double expectedValueExpense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Double getExpectedValueExpense() {
        return expectedValueExpense;
    }

    public void setExpectedValueExpense(Double expectedValueExpense) {
        this.expectedValueExpense = expectedValueExpense;
    }
}
