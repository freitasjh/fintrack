package br.com.systec.fintrack.financial.recurringtransaction.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RecurringTransactionRepository extends AbstractRepository<RecurringTransaction, Long> {

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFromTransactionIdAndTenant(Long transactionId) {
        Query query = entityManager.createQuery("delete from RecurringTransaction where transactionId = :transactionId " +
                " and tenantId = :tenantId");

        query.setParameter("transactionId", transactionId);
        query.setParameter("tenantId", TenantContext.getTenant());

        query.executeUpdate();
    }
}
