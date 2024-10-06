package br.com.systec.fintrack.creditcard.transaction.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardTransactionRepository extends AbstractRepository<CreditCardTransaction, Long> {
}
