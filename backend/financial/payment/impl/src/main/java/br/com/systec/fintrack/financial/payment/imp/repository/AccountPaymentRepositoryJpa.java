package br.com.systec.fintrack.financial.payment.imp.repository;

import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPaymentRepositoryJpa extends JpaRepository<AccountPayment, Long>, JpaSpecificationExecutor<AccountPayment> {

}
