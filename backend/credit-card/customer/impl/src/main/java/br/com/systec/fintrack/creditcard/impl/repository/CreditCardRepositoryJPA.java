package br.com.systec.fintrack.creditcard.impl.repository;

import br.com.systec.fintrack.creditcard.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepositoryJPA extends JpaRepository<CreditCard, Long>, JpaSpecificationExecutor<CreditCard> {
}
