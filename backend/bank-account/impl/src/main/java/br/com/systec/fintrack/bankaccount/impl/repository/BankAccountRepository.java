package br.com.systec.fintrack.bankaccount.impl.repository;

import br.com.systec.fintrack.bankaccount.model.AccountType;
import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BankAccountRepository extends AbstractRepository<BankAccount, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findCurrentAccountBalance() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select SUM(obj.balance) from BankAccount obj ");
        sql.append(" where obj.tenantId = :tenantId and obj.accountType = :accountType");

        TypedQuery<Double> query = entityManager.createQuery(sql.toString(), Double.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("accountType", AccountType.CURRENT_ACCOUNT);

        return query.getSingleResult();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNewBalance(Long bankAccountId, double newBalance) {
        StringBuilder sql = new StringBuilder();
        sql.append("update BankAccount set balance = :newBalance where id = :bankAccountId");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("newBalance", newBalance);
        query.setParameter("bankAccountId", bankAccountId);

        query.executeUpdate();
    }
}
