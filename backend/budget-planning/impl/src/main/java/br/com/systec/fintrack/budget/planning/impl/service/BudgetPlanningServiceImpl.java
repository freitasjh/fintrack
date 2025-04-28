package br.com.systec.fintrack.budget.planning.impl.service;

import br.com.systec.fintrack.budget.planning.api.filter.BudgetPlanningPageParam;
import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanning;
import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanningCategory;
import br.com.systec.fintrack.budget.planning.api.service.BudgetPlanningService;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningExpenseVO;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningVO;
import br.com.systec.fintrack.budget.planning.impl.mapper.BudgetPlanningMapper;
import br.com.systec.fintrack.budget.planning.impl.repository.BudgetPlanningRepository;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.commons.query.PaginatedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
public class BudgetPlanningServiceImpl implements BudgetPlanningService {

    private final BudgetPlanningRepository repository;

    public BudgetPlanningServiceImpl(BudgetPlanningRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BudgetPlanningVO save(BudgetPlanningVO budgetPlanning) throws BaseException {
        BudgetPlanning budgetPlanningToSave = BudgetPlanningMapper.toModel(budgetPlanning);

        for(BudgetPlanningCategory budgetPlaningCategory : budgetPlanningToSave.getListOfBudgetPlaningCategory()) {
            budgetPlaningCategory.setBudgetPlanning(budgetPlanningToSave);
        }

        BudgetPlanning budgetPlanningAfterSave = repository.save(budgetPlanningToSave);

        return BudgetPlanningMapper.toVO(budgetPlanningAfterSave);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BudgetPlanningVO update(BudgetPlanningVO budgetPlanning) throws BaseException {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public BudgetPlanningVO findById(Long id) throws BaseException {
        return BudgetPlanningMapper.toVO(findBudgetPlaningById(id));
    }

    private BudgetPlanning findBudgetPlaningById(Long id) throws BaseException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Budget Planning not found"));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BudgetPlanningVO> findAll() throws BaseException {
        List<BudgetPlanning> listOfBudgetPlanning = StreamSupport.stream(repository.findAllByTenantId().spliterator(), false).toList();

        return BudgetPlanningMapper.toListVO(listOfBudgetPlanning);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PaginatedList<BudgetPlanningVO> findByFilter(BudgetPlanningPageParam pageParam) throws BaseException {
        try {
            return BudgetPlanningMapper.toPaginatedVO(repository.findByFilter(pageParam));
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BudgetPlanningExpenseVO findBudgetPlanningExpenseByMouthAndYear(String monthAndYear) throws BaseException {
        BudgetPlanning budgetPlanning = repository.findBudgetPlanningByMonthAndYearAndTenant(monthAndYear)
                .orElseThrow(() -> new ObjectNotFoundException("Plano orçamentário não encontrado"));

        BudgetPlanningExpenseVO budgetPlanningExpenseVO = new BudgetPlanningExpenseVO();

        Double totalExpenseFixedByMouthAndYear = repository.findTotalExpenseFixedByMouthAndYear(monthAndYear);
        Double totalCreditCardExpensedByMonthAndYear = repository.findTotalCreditCardExpensedByMonthAndYear(monthAndYear);
        double remainingExpense = budgetPlanning.getExpectedValueExpense() - totalExpenseFixedByMouthAndYear;

        budgetPlanningExpenseVO.setTotalExpense(totalExpenseFixedByMouthAndYear);
        budgetPlanningExpenseVO.setTotalCreditCardExpense(totalCreditCardExpensedByMonthAndYear);
        budgetPlanningExpenseVO.setExpectedValueExpense(budgetPlanning.getExpectedValueExpense());
        budgetPlanningExpenseVO.setRemainingExpense(remainingExpense);

        return budgetPlanningExpenseVO;
    }
}
