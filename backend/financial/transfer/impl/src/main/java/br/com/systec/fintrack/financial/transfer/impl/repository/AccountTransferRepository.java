package br.com.systec.fintrack.financial.transfer.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountTransferRepository extends AbstractRepository<AccountTransfer, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountTransfer> findAllByTenant() {
        StringBuilder sql = new StringBuilder();
        sql.append("Select obj from AccountTransfer obj where obj.tenantId = :tenantId");

        TypedQuery<AccountTransfer> query = entityManager.createQuery(sql.toString(), AccountTransfer.class);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }
}
