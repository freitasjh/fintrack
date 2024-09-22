package br.com.systec.controle.financeiro.financial.creditCard.transaction.repository;

import br.com.systec.controle.financeiro.commons.AbstractRepository;
import br.com.systec.controle.financeiro.financial.creditCard.transaction.model.CreditCardInstallment;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardInstallmentRepository extends AbstractRepository<CreditCardInstallment, Long> {
}
