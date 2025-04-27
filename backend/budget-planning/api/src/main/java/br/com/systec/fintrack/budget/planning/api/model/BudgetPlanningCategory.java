package br.com.systec.fintrack.budget.planning.api.model;

import br.com.systec.fintrack.commons.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity
@Table(name = "budget_planning_category")
public class BudgetPlanningCategory extends BaseModel {

    @Serial
    private static final long serialVersionUID = -8309786468298780624L;
    @Column(name = "expected_value_expense")
    private Double expectedValueExpense;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "category_description")
    private String categoryDescription;
    @Column(name = "budget_planning_id")
    private BudgetPlanning budgetPlanning;

    public Double getExpectedValueExpense() {
        return expectedValueExpense;
    }

    public void setExpectedValueExpense(Double expectedValueExpense) {
        this.expectedValueExpense = expectedValueExpense;
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

    public BudgetPlanning getBudgetPlanning() {
        return budgetPlanning;
    }

    public void setBudgetPlanning(BudgetPlanning budgetPlanning) {
        this.budgetPlanning = budgetPlanning;
    }
}
