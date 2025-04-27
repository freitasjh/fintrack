package br.com.systec.fintrack.budget.planning.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class BudgetPlanningExpenseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5550639150383503075L;
    private double totalExpense;
    private double totalCreditCardExpense;
    private double remainingExpense;
    private double expectedValueExpense;

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getTotalCreditCardExpense() {
        return totalCreditCardExpense;
    }

    public void setTotalCreditCardExpense(double totalCreditCardExpense) {
        this.totalCreditCardExpense = totalCreditCardExpense;
    }

    public double getRemainingExpense() {
        return remainingExpense;
    }

    public void setRemainingExpense(double remainingExpense) {
        this.remainingExpense = remainingExpense;
    }

    public double getExpectedValueExpense() {
        return expectedValueExpense;
    }

    public void setExpectedValueExpense(double expectedValueExpense) {
        this.expectedValueExpense = expectedValueExpense;
    }
}
