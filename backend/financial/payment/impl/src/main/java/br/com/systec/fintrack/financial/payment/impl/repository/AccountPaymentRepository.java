package br.com.systec.fintrack.financial.payment.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        sql.append(" select SUM(obj.amount) from AccountPayment obj where obj.tenantId = :tenantId ");
        sql.append(" and obj.processed = :isProcessed ");
        sql.append(" and FUNCTION('DATE_FORMAT', obj.dateProcessed, '%m-%Y') = :mouthYear ");
        sql.append(" and obj.categoryTransactionType <> :transferType");

        TypedQuery<Double> query = entityManager.createQuery(sql.toString(), Double.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("isProcessed", true);
        query.setParameter("mouthYear", new SimpleDateFormat("MM-yyyy").format(new Date()));
        query.setParameter("transferType", CategoryTransactionType.TRANSFER);

        Double totalExpenseReturn = query.getSingleResult();
        if(totalExpenseReturn == null){
            return 0.0;
        }
        return query.getSingleResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findTotalPaymentNotProcessed() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select SUM(obj.amount) from AccountPayment obj where obj.tenantId = :tenantId");
        sql.append(" and obj.processed = :isProcessed ");

        TypedQuery<Double> query = entityManager.createQuery(sql.toString(), Double.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("isProcessed", false);

        return query.getSingleResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountPayment> findLastTenPayment() {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select obj from AccountPayment obj where obj.tenantId = :tenantId ");
        sql.append(" and obj.processed = :isProcessed ");
        sql.append(" and obj.categoryTransactionType <> :transferType");
        sql.append(" order by obj.dateProcessed Desc");

        TypedQuery<AccountPayment> query = entityManager.createQuery(sql.toString(), AccountPayment.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("isProcessed", true);
        query.setParameter("transferType", CategoryTransactionType.TRANSFER);
        query.setMaxResults(10);

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AccountPayment> findAccountPaymentOpen() {
        StringBuilder sql = new StringBuilder();
        sql.append("Select obj from AccountPayment obj where obj.tenantId = :tenantId ");
        sql.append("and obj.processed = :isProcessed ");
        sql.append(" order by obj.paymentDueDate desc ");

        TypedQuery<AccountPayment> query = entityManager.createQuery(sql.toString(), AccountPayment.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("isProcessed", false);

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AccountPayment> findAccountPaymentPending() {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select obj from AccountPayment obj where obj.tenantId = :tenantId ");
        sql.append(" and obj.paymentDueDate < :dateNow and obj.processed = :isProcessed");

        TypedQuery<AccountPayment> query = entityManager.createQuery(sql.toString(), AccountPayment.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("dateNow", new Date());
        query.setParameter("isProcessed", false);

        return query.getResultList();
    }
}
