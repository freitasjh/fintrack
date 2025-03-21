package br.com.systec.fintrack.creditcard.invoice.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO;
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
    public Optional<CreditCardInvoice> findByDateClose(LocalDate dateTransaction, Long creditCardId) {
        String sql = " Select obj from CreditCardInvoice obj " +
                " join fetch obj.creditCard cc " +
                " where obj.tenantId = :tenantId " +
                " and obj.dateClose >= :dateTransaction " +
                " and obj.creditCard.id = :creditCardId ";

        TypedQuery<CreditCardInvoice> query = entityManager.createQuery(sql, CreditCardInvoice.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("dateTransaction", dateTransaction);
        query.setParameter("creditCardId", creditCardId);

        List<CreditCardInvoice> listOfCreditCardInvoice = query.getResultList();

        if(listOfCreditCardInvoice.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfCreditCardInvoice.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findAmountInvoiceOpen() {
        String sql = "SELECT \n" +
                "    COALESCE(SUM(credit_card_installment.amount),0) AS amount\n" +
                "FROM \n" +
                "    credit_card_invoice cci\n" +
                "LEFT JOIN \n" +
                "    credit_card_installment ON credit_card_installment.credit_card_invoice_id = cci.id\n" +
                "where cci.status_type = :statusType and cci.tenant_id = :tenantId";

        Query query = entityManager.createNativeQuery(sql, Double.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("statusType", InvoiceStatusType.OPEN.ordinal());

        return (Double) query.getSingleResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInvoiceResultVO> findInvoiceByFilter(CreditCardInvoiceFilterVO filterVO) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putIfAbsent("tenantId", TenantContext.getTenant());

        StringBuilder sql = new StringBuilder();
        sql.append(" select new br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO( ");
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

        TypedQuery<CreditCardInvoiceResultVO> query = entityManager.createQuery(sql.toString(), CreditCardInvoiceResultVO.class);
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

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<CreditCardInvoiceResultVO> findCreditCardInvoiceById(Long creditCardInvoiceId) {
        StringBuilder sql = getQueryCreditCardInvoiceVO();

        TypedQuery<CreditCardInvoiceResultVO> query = entityManager.createQuery(sql.toString(), CreditCardInvoiceResultVO.class);
        query.setParameter("creditCardInvoiceId", creditCardInvoiceId);
        List<CreditCardInvoiceResultVO> listInvoiceReturn = query.getResultList();

        if(listInvoiceReturn.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listInvoiceReturn.get(0));
    }

    private static StringBuilder getQueryCreditCardInvoiceVO() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select new br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO( ");
        sql.append(" invoice.id, invoice.dueDate, invoice.statusType, cc.name, sum(installment.amount) ");
        sql.append(" ) ");
        sql.append(" from CreditCardInstallment installment ");
        sql.append(" join CreditCardInvoice invoice on invoice.id = installment.creditCardInvoiceId ");
        sql.append(" join invoice.creditCard cc ");
        sql.append(" where invoice.id = :creditCardInvoiceId ");

        return sql;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void creditCardInvoicePay(Long creditCardInvoiceId) {
        String sql = "update CreditCardInvoice set statusType = :statusType, datePaid = :datePay where id = :creditCardInvoiceId";

        Query query = entityManager.createQuery(sql);
        query.setParameter("statusType", InvoiceStatusType.PAID);
        query.setParameter("datePay", LocalDate.now());
        query.setParameter("creditCardInvoiceId", creditCardInvoiceId);

        query.executeUpdate();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<CreditCardInvoice> findById(Long id) {
        TypedQuery<CreditCardInvoice> query = entityManager.createQuery(
                "select obj from CreditCardInvoice obj where obj.id = :invoiceId",
                CreditCardInvoice.class
        );

        query.setParameter("invoiceId", id);

        List<CreditCardInvoice> results = query.getResultList();

        if(query.getResultList().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInvoice> findInvoicePendingPayment(Long creditCardId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select obj from CreditCardInvoice obj ");
        sql.append(" where obj.statusType = :statusType ");
        sql.append(" and obj.tenantId = :tenantId ");
        sql.append(" and obj.dueDate < :currentDate ");
        sql.append(" and obj.creditCard.id = :creditCardId ");

        TypedQuery<CreditCardInvoice> query = entityManager.createQuery(sql.toString(), CreditCardInvoice.class);
        query.setParameter("statusType", InvoiceStatusType.OPEN);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("currentDate", LocalDate.now());
        query.setParameter("creditCardId", creditCardId);

        return query.getResultList();
    }
}