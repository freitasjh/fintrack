package br.com.systec.fintrack.creditcard.transaction.impl.repository;

import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardTransactionRepositoryJPA extends JpaRepository<CreditCardTransaction, Long>, JpaSpecificationExecutor<CreditCardTransaction> {
}
