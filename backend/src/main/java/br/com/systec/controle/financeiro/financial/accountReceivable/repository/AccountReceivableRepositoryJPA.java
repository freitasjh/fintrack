package br.com.systec.controle.financeiro.AccountReceivable.repository;

import br.com.systec.controle.financeiro.AccountReceivable.model.Receive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiveRepositoryJPA extends JpaRepository<Receive, Long>, JpaSpecificationExecutor<Receive> {
}
