package br.com.systec.fintrack.administrator.bank.repository;

import br.com.systec.fintrack.administrator.bank.model.Bank;
import br.com.systec.fintrack.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BankRepository extends AbstractRepository<Bank, Long> {
}
