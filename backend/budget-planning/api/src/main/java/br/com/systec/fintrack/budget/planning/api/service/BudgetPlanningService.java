package br.com.systec.fintrack.budget.planning.api.service;

import br.com.systec.fintrack.budget.planning.api.filter.BudgetPlanningPageParam;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningExpenseVO;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningVO;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.query.PaginatedList;

import java.util.List;

public interface BudgetPlanningService {

    BudgetPlanningVO save(BudgetPlanningVO budgetPlanning) throws BaseException;

    BudgetPlanningVO update(BudgetPlanningVO budgetPlanning) throws BaseException;

    BudgetPlanningVO findById(Long id) throws BaseException;

    List<BudgetPlanningVO> findAll() throws BaseException;

    PaginatedList<BudgetPlanningVO> findByFilter(BudgetPlanningPageParam pageParam) throws BaseException;

    BudgetPlanningExpenseVO findBudgetPlanningExpenseByMouthAndYear(String mouthAndYear) throws BaseException;
}
