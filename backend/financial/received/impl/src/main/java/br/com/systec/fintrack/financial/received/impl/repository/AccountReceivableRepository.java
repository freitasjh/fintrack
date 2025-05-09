package br.com.systec.fintrack.financial.received.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountReceivableRepository extends AbstractRepository<AccountReceivable, Long> {
    private static final TransactionType TRANSACTION_TYPE = TransactionType.INCOMING;
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountReceivable> findAll(Long tenantId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select obj from AccountReceivable obj where obj.tenantId = :tenantId and obj.transactionType = :transactionType");

        TypedQuery<AccountReceivable> query = entityManager.createQuery(sql.toString(), AccountReceivable.class);
        query.setParameter("tenantId", tenantId);
        query.setParameter("transactionType", TRANSACTION_TYPE);

        return query.getResultList();
    }
}
