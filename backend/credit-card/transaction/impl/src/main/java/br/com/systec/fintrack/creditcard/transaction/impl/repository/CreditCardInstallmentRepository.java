package br.com.systec.fintrack.creditcard.transaction.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardInstallment;
import org.springframework.stereotype.Repository;

@Repository
public class CreditCardInstallmentRepository extends AbstractRepository<CreditCardInstallment, Long> {
}
