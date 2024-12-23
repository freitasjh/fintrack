package br.com.systec.fintrack.dashboard.api.v1.dto;

public class CategoryExpenseDTO {
    private String description;
    private double totalExpense;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
