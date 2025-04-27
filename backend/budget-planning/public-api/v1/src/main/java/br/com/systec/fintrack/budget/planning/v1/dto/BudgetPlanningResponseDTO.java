package br.com.systec.fintrack.budget.planning.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class BudgetPlanningResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5139839386874463457L;

    private Long id;
    private String description;
    private String mouthAndYearPlaning;
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
