package br.com.systec.controle.financeiro.administrator.bank.repository;

import br.com.systec.controle.financeiro.administrator.bank.model.Bank;
import br.com.systec.controle.financeiro.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BankRepository extends AbstractRepository<Bank, Long> {
}
