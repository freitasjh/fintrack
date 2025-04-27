package br.com.systec.fintrack.budget.planning.impl.mapper;

import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanning;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningVO;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.query.PaginatedList;

import java.util.List;

public class BudgetPlanningMapper {

    public static BudgetPlanningVO toVO(BudgetPlanning budgetPlanning) {
        BudgetPlanningVO budgetPlanningVO = new BudgetPlanningVO();
        budgetPlanningVO.setId(budgetPlanning.getId());
        budgetPlanningVO.setDescription(budgetPlanning.getDescription());
        budgetPlanningVO.setMouthAndYearPlaning(budgetPlanning.getMouthAndYearPlaning());
        budgetPlanningVO.setExpectedValueExpense(budgetPlanning.getExpectedValueExpense());
        budgetPlanningVO.setListOfBudgetPlaningCategory(BudgetPlanningCategoryMapper.toListVO(budgetPlanning.getListOfBudgetPlaningCategory()));

        return budgetPlanningVO;
    }


    public static List<BudgetPlanningVO> toListVO(List<BudgetPlanning> listOfBudgetPlanning) {
        return listOfBudgetPlanning.stream()
                .map(BudgetPlanningMapper::toVO)
                .toList();
    }

    public static BudgetPlanning toModel(BudgetPlanningVO budgetPlanningVO) {
        BudgetPlanning budgetPlanning = new BudgetPlanning();
        budgetPlanning.setId(budgetPlanningVO.getId());
        budgetPlanning.setDescription(budgetPlanningVO.getDescription());
        budgetPlanning.setMouthAndYearPlaning(budgetPlanningVO.getMouthAndYearPlaning());
        budgetPlanning.setExpectedValueExpense(budgetPlanningVO.getExpectedValueExpense());
        budgetPlanning.setListOfBudgetPlaningCategory(BudgetPlanningCategoryMapper.toListModel(budgetPlanningVO.getListOfBudgetPlaningCategory()));

        if(budgetPlanning.getTenantId() == null) {
            budgetPlanning.setTenantId(TenantContext.getTenant());
        }

        return budgetPlanning;
    }

    public static PaginatedList<BudgetPlanningVO> toPaginatedVO(PaginatedList<BudgetPlanning> paginatedList) {
        List<BudgetPlanningVO> listToConverter = paginatedList.getResultList().stream()
                .map(BudgetPlanningMapper::toVO).toList();
        PaginatedList<BudgetPlanningVO> paginatedListResponseDTO = new PaginatedList<>();

        paginatedListResponseDTO.addAll(listToConverter);
        paginatedListResponseDTO.setHasNext(paginatedList.isHasNext());
        paginatedListResponseDTO.setPageSizeResult(paginatedList.getPageSizeResult());
        paginatedListResponseDTO.setTotalResults(paginatedList.getTotalResults());

        return paginatedListResponseDTO;
    }

}
