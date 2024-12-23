package br.com.systec.fintrack.creditcard.invoice.impl.repository;

import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardInvoiceRepositoryJPA extends JpaRepository<CreditCardInvoice, Long>, JpaSpecificationExecutor<CreditCardInvoice> {
}
