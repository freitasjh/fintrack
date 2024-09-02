package br.com.systec.controle.financeiro.financial.accountTransfer.repository;

import br.com.systec.controle.financeiro.financial.accountTransfer.model.AccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransferRepositoryJPA extends JpaRepository<AccountTransfer, Long>, JpaSpecificationExecutor<AccountTransfer> {

}
