package br.com.systec.fintrack.budget.planning.impl.mapper;


import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanningCategory;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlaningCategoryVO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetPlanningCategoryMapperTest {

    @Test
    void toVOShouldMapAllFieldsCorrectly() {
        BudgetPlanningCategory category = new BudgetPlanningCategory();
        category.setId(1L);
        category.setCategoryId(2L);
        category.setCategoryDescription("Food");
        category.setExpectedValueExpense(500.0);

        BudgetPlaningCategoryVO result = BudgetPlanningCategoryMapper.toVO(category);

        assertEquals(1L, result.getId());
        assertEquals(2L, result.getCategoryId());
        assertEquals("Food", result.getCategoryDescription());
        assertEquals(500.0, result.getExpectedValueExpense());
    }

    @Test
    void toListVOShouldMapListOfCategoriesCorrectly() {
        BudgetPlanningCategory category1 = new BudgetPlanningCategory();
        category1.setId(1L);
        category1.setCategoryId(2L);
        category1.setCategoryDescription("Food");
        category1.setExpectedValueExpense(500.0);

        BudgetPlanningCategory category2 = new BudgetPlanningCategory();
        category2.setId(3L);
        category2.setCategoryId(4L);
        category2.setCategoryDescription("Transport");
        category2.setExpectedValueExpense(300.0);

        List<BudgetPlaningCategoryVO> result = BudgetPlanningCategoryMapper.toListVO(List.of(category1, category2));

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Food", result.get(0).getCategoryDescription());
        assertEquals(3L, result.get(1).getId());
        assertEquals("Transport", result.get(1).getCategoryDescription());
    }

    @Test
    void toListVOShouldHandleEmptyListInput() {
        List<BudgetPlaningCategoryVO> result = BudgetPlanningCategoryMapper.toListVO(List.of());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}