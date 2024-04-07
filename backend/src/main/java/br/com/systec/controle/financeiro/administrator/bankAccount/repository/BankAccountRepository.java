package br.com.systec.controle.financeiro.administrator.bankAccount.repository;

import br.com.systec.controle.financeiro.administrator.bankAccount.model.BankAccount;
import br.com.systec.controle.financeiro.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BankAccountRepository extends AbstractRepository<BankAccount, Long> {
}
