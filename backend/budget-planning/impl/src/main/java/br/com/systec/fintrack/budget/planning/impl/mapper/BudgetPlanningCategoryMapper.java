package br.com.systec.fintrack.budget.planning.impl.mapper;

import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanningCategory;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlaningCategoryVO;

import java.util.List;

public class BudgetPlanningCategoryMapper {

    public static BudgetPlaningCategoryVO toVO(BudgetPlanningCategory budgetPlaningCategory) {
        BudgetPlaningCategoryVO budgetPlaningCategoryVO = new BudgetPlaningCategoryVO();
        budgetPlaningCategoryVO.setId(budgetPlaningCategory.getId());
        budgetPlaningCategoryVO.setCategoryId(budgetPlaningCategory.getCategoryId());
        budgetPlaningCategoryVO.setCategoryDescription(budgetPlaningCategory.getCategoryDescription());
        budgetPlaningCategoryVO.setExpectedValueExpense(budgetPlaningCategory.getExpectedValueExpense());

        return budgetPlaningCategoryVO;
    }

    public static List<BudgetPlaningCategoryVO> toListVO(List<BudgetPlanningCategory> listOfBudgetPlaningCategory) {
        return listOfBudgetPlaningCategory.stream()
                .map(BudgetPlanningCategoryMapper::toVO)
                .toList();
    }

    public static BudgetPlanningCategory toModel(BudgetPlaningCategoryVO budgetPlaningCategoryVO) {
        BudgetPlanningCategory budgetPlaningCategory = new BudgetPlanningCategory();
        budgetPlaningCategory.setId(budgetPlaningCategoryVO.getId());
        budgetPlaningCategory.setCategoryId(budgetPlaningCategoryVO.getCategoryId());
        budgetPlaningCategory.setCategoryDescription(budgetPlaningCategoryVO.getCategoryDescription());
        budgetPlaningCategory.setExpectedValueExpense(budgetPlaningCategoryVO.getExpectedValueExpense());

        return budgetPlaningCategory;
    }

    public static List<BudgetPlanningCategory> toListModel(List<BudgetPlaningCategoryVO> listOfBudgetPlaningCategoryVO) {
        return listOfBudgetPlaningCategoryVO.stream()
                .map(BudgetPlanningCategoryMapper::toModel)
                .toList();
    }
}
