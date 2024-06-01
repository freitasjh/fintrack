package br.com.systec.controle.financeiro.financial.accountReceivable.repository;

import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReceivableRepositoryJPA extends JpaRepository<AccountReceivable, Long>, JpaSpecificationExecutor<AccountReceivable> {
}
