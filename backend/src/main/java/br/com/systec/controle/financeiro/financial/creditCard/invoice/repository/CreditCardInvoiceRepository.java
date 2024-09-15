package br.com.systec.controle.financeiro.financial.creditCard.invoice.repository;

import br.com.systec.controle.financeiro.commons.AbstractRepository;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.financial.creditCard.invoice.model.CreditCardInvoice;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
}
