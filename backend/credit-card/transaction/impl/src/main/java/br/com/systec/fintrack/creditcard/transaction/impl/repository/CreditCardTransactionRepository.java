package br.com.systec.fintrack.creditcard.transaction.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionPageParam;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CreditCardTransactionRepository extends AbstractRepository<CreditCardTransaction, Long> {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PaginatedList<CreditCardTransaction> findByFilter(CreditCardTransactionPageParam filterParam) {

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT obj FROM CreditCardTransaction obj join fetch obj.listOfInstallment ");
        sql.append(" where obj.tenantId = :tenantId ");

        filterParam.addParam("tenantId", TenantContext.getTenant());

        if(filterParam.getFilterVO().getKeyword() != null && !filterParam.getFilterVO().getKeyword().isEmpty()) {
            sql.append(" and obj.description like :keyword ");
            filterParam.addParam("keyword", "%" + filterParam.getFilterVO().getKeyword() + "%");
        }

        if(filterParam.getFilterVO().getCreditCardId() != null) {
            sql.append(" and  obj.creditCard = :creditCard ");
            filterParam.addParam("creditCard", filterParam.getFilterVO().getCreditCardId());
        }

        if(filterParam.getFilterVO().getDateFilter() != null) {
            sql.append(" and obj.dateCreated = :dateFilter ");
            filterParam.addParam("dateFilter", filterParam.getFilterVO().getDateFilter());
        }

        TypedQuery<CreditCardTransaction> query = entityManager.createQuery(sql.toString(), CreditCardTransaction.class);

        return executePaginatedQuery(query, filterParam);
    }
}
