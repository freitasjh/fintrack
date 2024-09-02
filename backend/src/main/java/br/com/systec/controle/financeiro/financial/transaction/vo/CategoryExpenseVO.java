package br.com.systec.controle.financeiro.financial.transaction.vo;

public class CategoryExpenseVO {
    private String categoryDescription;
    private double totalExpense;

    public CategoryExpenseVO() {

    }

    public CategoryExpenseVO(String categoryDescription, double totalExpense) {
        this.categoryDescription = categoryDescription;
        this.totalExpense = totalExpense;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
