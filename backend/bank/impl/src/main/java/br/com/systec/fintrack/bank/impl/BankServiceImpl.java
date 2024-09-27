package br.com.systec.fintrack.bank.impl;

import br.com.systec.fintrack.bank.filter.FilterBankVO;
import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bank.repository.BankRepository;
import br.com.systec.fintrack.bank.repository.BankRepositoryJPA;
import br.com.systec.fintrack.bank.service.BankService;
import br.com.systec.fintrack.commons.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository repository;
    @Autowired
    private BankRepositoryJPA repositoryJPA;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<Bank> findAll() throws BaseException{
        try{
            return repository.findAll();
        }catch (BaseException e) {
            throw new BaseException("Ocorreu um erro ao tentar retornar os bancos",e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Bank> findByPage(FilterBankVO filter) throws BaseException {
        try {
            Page<Bank> listOfBank = repositoryJPA.findAll(filter.getSpecification(), filter.getPageable());

            return listOfBank;
        }catch (Exception e) {
            throw new BaseException("", e);
        }

    }
}
