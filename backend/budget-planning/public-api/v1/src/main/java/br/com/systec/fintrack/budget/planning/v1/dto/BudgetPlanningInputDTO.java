package br.com.systec.fintrack.budget.planning.v1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public class BudgetPlanningInputDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2891785035099289119L;
    private Long id;
    @NotEmpty
    @NotNull
    private String description;
    @NotNull
    @NotNull
    @NotEmpty
    private String mouthAndYearPlaning;
    @Size(min = 1)
    @NotNull
    private Double expectedValueExpense;

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
}
