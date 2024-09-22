package br.com.systec.fintrack.administrator.bank.repository;

import br.com.systec.fintrack.administrator.bank.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepositoryJPA extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank> {
}
