package br.com.systec.fintrack.creditcard.transaction.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardInstallment;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CreditCardInstallmentRepository extends AbstractRepository<CreditCardInstallment, Long> {

    @Transactional(propagation = Propagation.REQUIRED)
    public void registerPaymentInstallmentsForInvoiceId(Long invoiceId, LocalDate datePay) {
        String sql = "update CreditCardInstallment set datePaid = : datePay where creditCardInvoiceId = :invoiceId";

        Query query = entityManager.createQuery(sql);
        query.setParameter("datePay", datePay);
        query.setParameter("invoiceId", invoiceId);

        query.executeUpdate();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void bindInstallmentsToCreditCardInvoiceId(Long creditCardInvoiceId, Long creditCardId, LocalDate dueDate) {
        String sql = " update credit_card_installment " +
                " join credit_card_transaction on credit_card_transaction.id = credit_card_installment.transaction_id " +
                " set credit_card_installment.credit_card_invoice_id = :invoiceId " +
                " where credit_card_transaction.credit_card_id = :creditCardId and credit_card_installment.due_date = :dueDate " +
                " and credit_card_transaction.tenant_id = :tenantId and credit_card_installment.credit_card_invoice_id is null ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("invoiceId", creditCardInvoiceId);
        query.setParameter("creditCardId", creditCardId);
        query.setParameter("dueDate", dueDate);
        query.setParameter("tenantId", TenantContext.getTenant());

        query.executeUpdate();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInstallment> findInstallmentPendingPayment(Long creditCardId) {
        String sql = " Select obj from CreditCardInstallment obj " +
                " join CreditCardTransaction cct on cct.id = obj.transaction.id " +
                " where cct.creditCard.id = :creditCardId and obj.dueDate < :currentDate ";

        TypedQuery<CreditCardInstallment> query = entityManager.createQuery(sql, CreditCardInstallment.class);
        query.setParameter("creditCardId", creditCardId);
        query.setParameter("currentDate", LocalDate.now());

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInstallment> findInstallmentByCreditCardInvoiceId(Long creditCardInvoiceId) {
        String sql = " Select obj from CreditCardInstallment obj " +
                " where obj.creditCardInvoiceId = :creditCardInvoiceId ";

        TypedQuery<CreditCardInstallment> query = entityManager.createQuery(sql, CreditCardInstallment.class);
        query.setParameter("creditCardInvoiceId", creditCardInvoiceId);

        return query.getResultList();
    }
}
