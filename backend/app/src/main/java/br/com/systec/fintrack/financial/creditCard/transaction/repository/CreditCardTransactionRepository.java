package br.com.systec.fintrack.financial.creditCard.transaction.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.financial.creditCard.transaction.model.CreditCardTransaction;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardTransactionRepository extends AbstractRepository<CreditCardTransaction, Long> {
}
