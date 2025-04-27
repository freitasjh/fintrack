package br.com.systec.fintrack.budget.planning.v1.converter;

import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningVO;
import br.com.systec.fintrack.budget.planning.v1.dto.BudgetPlanningInputDTO;
import br.com.systec.fintrack.budget.planning.v1.dto.BudgetPlanningResponseDTO;
import br.com.systec.fintrack.commons.query.PaginatedList;

import java.util.ArrayList;
import java.util.List;

public class BudgetPlanningConverter {

    public static BudgetPlanningVO toVO(BudgetPlanningInputDTO budgetPlanningInputDTO) {
        BudgetPlanningVO budgetPlanningVO = new BudgetPlanningVO();
        budgetPlanningVO.setId(budgetPlanningInputDTO.getId());
        budgetPlanningVO.setDescription(budgetPlanningInputDTO.getDescription());
        budgetPlanningVO.setMouthAndYearPlaning(budgetPlanningInputDTO.getMouthAndYearPlaning());
        budgetPlanningVO.setExpectedValueExpense(budgetPlanningInputDTO.getExpectedValueExpense());

        budgetPlanningVO.setListOfBudgetPlaningCategory(new ArrayList<>());

        return budgetPlanningVO;
    }

    public static BudgetPlanningResponseDTO toResponseDTO( BudgetPlanningVO budgetPlanningVO) {
        BudgetPlanningResponseDTO budgetPlanningResponseDTO = new BudgetPlanningResponseDTO();
        budgetPlanningResponseDTO.setId(budgetPlanningVO.getId());
        budgetPlanningResponseDTO.setDescription(budgetPlanningVO.getDescription());
        budgetPlanningResponseDTO.setMouthAndYearPlaning(budgetPlanningVO.getMouthAndYearPlaning());
        budgetPlanningResponseDTO.setExpectedValueExpense(budgetPlanningVO.getExpectedValueExpense());

        return budgetPlanningResponseDTO;
    }

    public static PaginatedList<BudgetPlanningResponseDTO> toListResponseDTO(PaginatedList<BudgetPlanningVO> paginatedList) {
        List<BudgetPlanningResponseDTO> listToConverter = paginatedList.getResultList().stream().map(BudgetPlanningConverter::toResponseDTO).toList();
        PaginatedList<BudgetPlanningResponseDTO> paginatedListResponseDTO = new PaginatedList<>();

        paginatedListResponseDTO.addAll(listToConverter);
        paginatedListResponseDTO.setHasNext(paginatedList.isHasNext());
        paginatedListResponseDTO.setPageSizeResult(paginatedList.getPageSizeResult());
        paginatedListResponseDTO.setTotalResults(paginatedList.getTotalResults());

        return paginatedListResponseDTO;
    }
}
