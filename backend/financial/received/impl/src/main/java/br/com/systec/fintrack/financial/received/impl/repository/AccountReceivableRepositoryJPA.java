package br.com.systec.fintrack.financial.received.impl.repository;

import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReceivableRepositoryJPA extends JpaRepository<AccountReceivable, Long>, JpaSpecificationExecutor<AccountReceivable> {
}
