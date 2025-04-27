package br.com.systec.fintrack.budget.planning.impl.repository;

import br.com.systec.fintrack.budget.planning.api.filter.BudgetPlanningPageParam;
import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanning;
import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.commons.utils.DateUtils;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BudgetPlanningRepository extends AbstractRepository<BudgetPlanning, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PaginatedList<BudgetPlanning> findByFilter(BudgetPlanningPageParam filterParam) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT obj FROM BudgetPlanning obj ");
        sql.append(" where obj.tenantId = :tenantId ");
        filterParam.addParam("tenantId", TenantContext.getTenant());


        TypedQuery<BudgetPlanning> query = entityManager.createQuery(sql.toString(), BudgetPlanning.class);

        return executePaginatedQuery(query, filterParam);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findTotalExpenseFixedByMouthAndYear(String mouthyAndYear) {
        String sql = "SELECT COALESCE(SUM(t.amount), 0) AS total\n" +
                "  FROM `transaction` t\n" +
                "  WHERE t.transaction_type = :transactionType\n" +
                "    AND t.processed = :processed\n" +
                "    AND t.tenant_id = :tenantId\n" +
                "    AND t.date_processed >= :dateFilterInitial\n" +
                "    AND t.date_processed <= :dateFilterFinal";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("transactionType", TransactionType.EXPENSE.ordinal());
        query.setParameter("dateFilterInitial", DateUtils.convertToDate(mouthyAndYear, 1));
        query.setParameter("dateFilterFinal", DateUtils.convertToDate(mouthyAndYear, 31));
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("processed", true);

        return (Double) query.getSingleResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findTotalCreditCardExpensedByMonthAndYear(String monthAndYear) {
        String sql = "SELECT COALESCE(SUM(cct.amount), 0) AS total \n" +
                " FROM credit_card_transaction cct\n" +
                " WHERE cct.tenant_id = :tenantId\n" +
                " AND cct.date_transaction >= :dateFilterInitial\n" +
                " AND cct.date_transaction <= :dateFilterFinal";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("dateFilterInitial", DateUtils.convertToDate(monthAndYear, 1));
        query.setParameter("dateFilterFinal", DateUtils.convertToDate(monthAndYear, 31));

        return (Double) query.getSingleResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<BudgetPlanning> findBudgetPlanningByMonthAndYearAndTenant(String monthAndYear) {
        String sql = "SELECT obj FROM BudgetPlanning obj " +
                "WHERE obj.tenantId = :tenantId " +
                "AND obj.mouthAndYearPlaning = :monthAndYear";

        TypedQuery<BudgetPlanning> query = entityManager.createQuery(sql, BudgetPlanning.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("monthAndYear", monthAndYear);

        List<BudgetPlanning> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(resultList.get(0));
    }
}
