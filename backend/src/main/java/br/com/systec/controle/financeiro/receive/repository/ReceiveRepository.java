package br.com.systec.controle.financeiro.receive.repository;

import br.com.systec.controle.financeiro.commons.AbstractRepository;
import br.com.systec.controle.financeiro.receive.model.Receive;
import org.springframework.stereotype.Repository;

@Repository
public class ReceiveRepository extends AbstractRepository<Receive, Long> {
}
