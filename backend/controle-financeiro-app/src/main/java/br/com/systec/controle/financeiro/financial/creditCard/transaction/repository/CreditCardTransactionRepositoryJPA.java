package br.com.systec.controle.financeiro.financial.creditCard.transaction.repository;

import br.com.systec.controle.financeiro.financial.creditCard.transaction.model.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardTransactionRepositoryJPA extends JpaRepository<CreditCardTransaction, Long>, JpaSpecificationExecutor<CreditCardTransaction> {
}
