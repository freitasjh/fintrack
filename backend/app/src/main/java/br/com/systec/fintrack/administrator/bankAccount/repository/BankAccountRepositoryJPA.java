package br.com.systec.fintrack.administrator.bankAccount.repository;

import br.com.systec.fintrack.administrator.bankAccount.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepositoryJPA extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {
}
