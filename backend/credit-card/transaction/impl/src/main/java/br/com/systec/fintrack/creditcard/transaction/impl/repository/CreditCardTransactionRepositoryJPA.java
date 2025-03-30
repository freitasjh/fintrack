package br.com.systec.fintrack.creditcard.transaction.impl.repository;

import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardTransactionRepositoryJPA extends JpaRepository<CreditCardTransaction, Long>, JpaSpecificationExecutor<CreditCardTransaction> {

}
