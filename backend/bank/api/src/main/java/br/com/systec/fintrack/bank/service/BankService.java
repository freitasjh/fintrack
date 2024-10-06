package br.com.systec.fintrack.bank.service;

import br.com.systec.fintrack.bank.filter.FilterBankVO;
import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.commons.exception.BaseException;
import org.springframework.data.domain.Page;

public interface BankService {

    Iterable<Bank> findAll() throws BaseException;
    Page<Bank> findByPage(FilterBankVO filter) throws BaseException;
}
