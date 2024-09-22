package br.com.systec.controle.financeiro.financial.creditCard.transaction.repository;

import br.com.systec.controle.financeiro.commons.AbstractRepository;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.model.CreditCardTransaction;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardTransactionRepository extends AbstractRepository<CreditCardTransaction, Long> {
}
