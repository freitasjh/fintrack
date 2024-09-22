package br.com.systec.fintrack.creditCard.repository;

import br.com.systec.fintrack.creditCard.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepositoryJPA extends JpaRepository<CreditCard, Long>, JpaSpecificationExecutor<CreditCard> {
}
