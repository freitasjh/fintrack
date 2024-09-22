package br.com.systec.fintrack.administrator.bank.service;

import br.com.systec.fintrack.administrator.bank.filter.FilterBankVO;
import br.com.systec.fintrack.administrator.bank.model.Bank;
import br.com.systec.fintrack.administrator.bank.repository.BankRepository;
import br.com.systec.fintrack.administrator.bank.repository.BankRepositoryJPA;
import br.com.systec.fintrack.commons.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;
    @Autowired
    private BankRepositoryJPA repositoryJPA;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<Bank> findAll() {
        try{
            return repository.findAll();
        }catch (BaseException e) {
            throw new BaseException("Ocorreu um erro ao tentar retornar os bancos",e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Bank> findByPage(FilterBankVO filter) {
        try {
            Page<Bank> listOfBank = repositoryJPA.findAll(filter.getSpecification(), filter.getPageable());

            return listOfBank;
        }catch (Exception e) {
            throw new BaseException("", e);
        }

    }
}
