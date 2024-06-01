package br.com.systec.controle.financeiro.AccountReceivable.repository;

import br.com.systec.controle.financeiro.commons.AbstractRepository;
import br.com.systec.controle.financeiro.AccountReceivable.model.Receive;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiveRepository extends AbstractRepository<Receive, Long> {
}
