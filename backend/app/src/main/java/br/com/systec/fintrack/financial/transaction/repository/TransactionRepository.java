package br.com.systec.fintrack.financial.transaction.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.transaction.enums.TransactionType;
import br.com.systec.fintrack.financial.transaction.model.Transaction;
import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TransactionRepository extends AbstractRepository<Transaction, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CategoryExpenseVO> findTotalExpenseCategory() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select new br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO(c.description, SUM(t.amount)) ");
        sql.append(" from br.com.systec.fintrack.financial.transaction.model.Transaction t ");
        sql.append(" join Category c on t.category.id = c.id ");
        sql.append(" where t.transactionType = :transactionType and t.tenantId = :tenantId ");
        sql.append(" group by c.description ");

        TypedQuery<CategoryExpenseVO> query = entityManager.createQuery(sql.toString(), CategoryExpenseVO.class);
        query.setParameter("transactionType", TransactionType.EXPENSE);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }
}
