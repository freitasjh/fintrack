package br.com.systec.controle.financeiro.financial.accountPayment.repository;

import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPaymentRepositoryJpa extends JpaRepository<AccountPayment, Long>, JpaSpecificationExecutor<AccountPayment> {

}
