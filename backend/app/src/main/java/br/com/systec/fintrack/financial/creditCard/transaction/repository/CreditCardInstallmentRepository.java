package br.com.systec.fintrack.financial.creditCard.transaction.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.financial.creditCard.transaction.model.CreditCardInstallment;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardInstallmentRepository extends AbstractRepository<CreditCardInstallment, Long> {
}
