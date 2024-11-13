package br.com.systec.fintrack.creditcard.invoice.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.model.InvoiceStatusType;
import br.com.systec.fintrack.invoice.vo.filter.CreditCardInvoiceFilterVO;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CreditCardInvoiceRepository extends AbstractRepository<CreditCardInvoice, Long> {


    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<CreditCardInvoice> findByDateClose(LocalDate dateTransaction) {
        StringBuilder sql = new StringBuilder();

        sql.append(" Select obj from CreditCardInvoice obj ");
        sql.append(" where obj.tenantId = :tenantId ");
        sql.append(" and obj.dateClose >= :dateTransaction");

        TypedQuery<CreditCardInvoice> query = entityManager.createQuery(sql.toString(), CreditCardInvoice.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("dateTransaction", dateTransaction);

        List<CreditCardInvoice> listOfCreditCardInvoice = query.getResultList();

        if(listOfCreditCardInvoice.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfCreditCardInvoice.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findAmountInvoiceOpen() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n" +
                "    COALESCE(SUM(credit_card_installment.amount),0) AS amount\n" +
                "FROM \n" +
                "    credit_card_invoice cci\n" +
                "LEFT JOIN \n" +
                "    credit_card_installment ON credit_card_installment.credit_card_invoice_id = cci.id\n" +
                "where cci.status_type = :statusType and cci.tenant_id = :tenantId");

        Query query = entityManager.createNativeQuery(sql.toString(), Double.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("statusType", InvoiceStatusType.OPEN.ordinal());

        return (Double) query.getSingleResult();

    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInvoiceVO> findInvoiceByFilter(CreditCardInvoiceFilterVO filterVO) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putIfAbsent("tenantId", TenantContext.getTenant());

        StringBuilder sql = new StringBuilder();
        sql.append(" select new br.com.systec.fintrack.invoice.vo.CreditCardInvoiceVO( ");
        sql.append(" invoice.id, invoice.dueDate, invoice.statusType, cc.name, sum(installment.amount) ");
        sql.append(" ) ");
        sql.append(" from CreditCardInstallment installment ");
        sql.append(" join CreditCardInvoice invoice on invoice.id = installment.creditCardInvoiceId ");
        sql.append(" join invoice.creditCard cc ");
        sql.append(" where invoice.tenantId = :tenantId ");

        if(filterVO.getCreditCardId() != null){
            sql.append("and cc.id = :creditCardId");
            queryParams.putIfAbsent("creditCardId", filterVO.getCreditCardId());
        }

        sql.append(" GROUP BY invoice.id, invoice.dueDate, invoice.statusType, cc.name ");

        TypedQuery<CreditCardInvoiceVO> query = entityManager.createQuery(sql.toString(), CreditCardInvoiceVO.class);
        setParametersOnQuery(query, queryParams);

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardFutureInvoiceVO> findAllFutureInvoice(LocalDate dateFilter) {
        StringBuilder sql = new StringBuilder(" ");
        sql.append(" select new br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO( ");
        sql.append(" installment.dueDate, creditCard.name, sum(installment.amount) ");
        sql.append(" ) ");
        sql.append(" from CreditCardInstallment installment ");
        sql.append(" join installment.transaction transaction ");
        sql.append(" join transaction.creditCard creditCard ");
        sql.append(" where transaction.tenantId = :tenantId ");
        sql.append(" group by installment.dueDate, creditCard.name");

        TypedQuery<CreditCardFutureInvoiceVO> query = entityManager.createQuery(sql.toString(), CreditCardFutureInvoiceVO.class);
        query.setParameter("tenantId", TenantContext.getTenant());

        return query.getResultList();
    }
}
