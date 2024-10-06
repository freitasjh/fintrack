package br.com.systec.fintrack.financial.transfer.impl.repository;

import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransferRepositoryJPA extends JpaRepository<AccountTransfer, Long>, JpaSpecificationExecutor<AccountTransfer> {

}
