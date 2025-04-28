package br.com.systec.fintrack.bankaccount.impl.repository;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepositoryJPA extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {
}
