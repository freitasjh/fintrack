package br.com.systec.fintrack.creditcard.impl.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CreditCardRepository extends AbstractRepository<CreditCard, Long> {

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCreditCardAvailableLimit(double newLimitAvailable, Long creditCardId) {
        Query query = entityManager.createQuery("update CreditCard set availableLimit = :newLimit where id = :creditCardId");
        query.setParameter("newLimit", newLimitAvailable);
        query.setParameter("creditCardId", creditCardId);

        query.executeUpdate();
    }
}
