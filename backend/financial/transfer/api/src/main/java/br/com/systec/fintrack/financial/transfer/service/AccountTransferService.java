package br.com.systec.fintrack.financial.transfer.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.transfer.exceptions.AccountTransferFindException;
import br.com.systec.fintrack.financial.transfer.filters.AccountTransferFilterVO;
import br.com.systec.fintrack.financial.transfer.model.AccountTransfer;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountTransferService {
    AccountTransfer save(AccountTransfer accountTransfer) throws BaseException;
    List<AccountTransfer> findAll() throws AccountTransferFindException;
    Page<AccountTransfer> findByFilter(AccountTransferFilterVO filterVO) throws AccountTransferFindException;
}