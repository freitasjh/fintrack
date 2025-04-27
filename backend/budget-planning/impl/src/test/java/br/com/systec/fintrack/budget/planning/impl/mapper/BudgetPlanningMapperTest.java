package br.com.systec.fintrack.budget.planning.impl.mapper;

import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanningCategory;
import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanning;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningVO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BudgetPlanningMapperTest {

    @Test
    void toVOShouldMapAllFieldsOfBudgetPlanningCorrectly() {
        BudgetPlanning budgetPlanning = new BudgetPlanning();
        budgetPlanning.setId(1L);
        budgetPlanning.setDescription("Monthly Budget");
        budgetPlanning.setMouthAndYearPlaning("2023-10");
        budgetPlanning.setExpectedValueExpense(1000.0);
        budgetPlanning.setListOfBudgetPlaningCategory(List.of(new BudgetPlanningCategory()));

        BudgetPlanningVO result = BudgetPlanningMapper.toVO(budgetPlanning);

        assertEquals(1L, result.getId());
        assertEquals("Monthly Budget", result.getDescription());
        assertEquals("2023-10", result.getMouthAndYearPlaning());
        assertEquals(1000.0, result.getExpectedValueExpense());
        assertNotNull(result.getListOfBudgetPlaningCategory());
    }


    @Test
    void toListVOShouldMapListOfBudgetPlanningCorrectly() {
        BudgetPlanning budgetPlanning1 = new BudgetPlanning();
        budgetPlanning1.setId(1L);
        budgetPlanning1.setDescription("Budget 1");
        budgetPlanning1.setMouthAndYearPlaning("2023-10");
        budgetPlanning1.setExpectedValueExpense(1000.0);
        budgetPlanning1.setListOfBudgetPlaningCategory(List.of(new BudgetPlanningCategory()));

        BudgetPlanning budgetPlanning2 = new BudgetPlanning();
        budgetPlanning2.setId(2L);
        budgetPlanning2.setDescription("Budget 2");
        budgetPlanning2.setMouthAndYearPlaning("2023-11");
        budgetPlanning2.setExpectedValueExpense(2000.0);
        budgetPlanning2.setListOfBudgetPlaningCategory(List.of(new BudgetPlanningCategory()));

        List<BudgetPlanningVO> result = BudgetPlanningMapper.toListVO(List.of(budgetPlanning1, budgetPlanning2));

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Budget 1", result.get(0).getDescription());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Budget 2", result.get(1).getDescription());
    }
}
