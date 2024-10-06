package br.com.systec.fintrack.financial.received.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableException;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountReceivableService {
    AccountReceivable save(AccountReceivable accountReceivable) throws AccountReceivableException;
    List<AccountReceivable> findAll() throws BaseException;
    Page<AccountReceivable> findByFilter(AccountReceivableFilterVO filterVO) throws BaseException;
    AccountReceivable findById(Long id) throws BaseException;
}
