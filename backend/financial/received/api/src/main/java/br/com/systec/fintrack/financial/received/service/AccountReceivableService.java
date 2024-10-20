package br.com.systec.fintrack.financial.received.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.financial.received.exceptions.AccountReceivableException;
import br.com.systec.fintrack.financial.received.filter.AccountReceivableFilterVO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountReceivableService {

    AccountReceivableVO save(AccountReceivableVO accountReceivable) throws AccountReceivableException;
    List<AccountReceivableVO> findAll() throws BaseException;
    Page<AccountReceivableVO> findByFilter(AccountReceivableFilterVO filterVO) throws BaseException;
    AccountReceivableVO findById(Long id) throws BaseException;

}
