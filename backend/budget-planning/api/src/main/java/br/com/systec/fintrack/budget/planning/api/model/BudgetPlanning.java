package br.com.systec.fintrack.budget.planning.api.model;

import br.com.systec.fintrack.commons.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.util.List;

@Entity
@Table(name = "budget_planning")
public class BudgetPlanning extends BaseModel {

    @Serial
    private static final long serialVersionUID = -3659059792360702479L;
    @Column(name = "description")
    private String description;
    @Column(name = "month_and_year_planing")
    private String mouthAndYearPlaning;
    @Column(name = "expected_value_expense")
    private double expectedValueExpense;
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;
    @OneToMany(mappedBy = "budgetPlanning", targetEntity = BudgetPlanningCategory.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.JOIN)
    private List<BudgetPlanningCategory> listOfBudgetPlaningCategory;

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

    public double getExpectedValueExpense() {
        return expectedValueExpense;
    }

    public void setExpectedValueExpense(double expectedValueExpense) {
        this.expectedValueExpense = expectedValueExpense;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<BudgetPlanningCategory> getListOfBudgetPlaningCategory() {
        return listOfBudgetPlaningCategory;
    }

    public void setListOfBudgetPlaningCategory(List<BudgetPlanningCategory> listOfBudgetPlaningCategory) {
        this.listOfBudgetPlaningCategory = listOfBudgetPlaningCategory;
    }
}
