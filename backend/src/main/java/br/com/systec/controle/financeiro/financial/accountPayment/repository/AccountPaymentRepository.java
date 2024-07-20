package br.com.systec.controle.financeiro.financial.accountPayment.repository;

import br.com.systec.controle.financeiro.commons.AbstractRepository;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.reflect.Typed;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountPaymentRepository extends AbstractRepository<AccountPayment, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountPayment> findAllByTenant() {
        StringBuilder sql = new StringBuilder();
        sql.append("Select obj from AccountPayment obj where obj.tenantId = :tenantId");

        TypedQuery<AccountPayment> query = entityManager.createQuery(sql.toString(), AccountPayment.class);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findMonthlyExpenses() {
        StringBuilder sql = new StringBuilder();
        sql.append("select SUM(obj.amount) from AccountPayment obj where obj.tenantId = :tenantId");

        TypedQuery<Double> query = entityManager.createQuery(sql.toString(), Double.class);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getSingleResult();
    }
}
