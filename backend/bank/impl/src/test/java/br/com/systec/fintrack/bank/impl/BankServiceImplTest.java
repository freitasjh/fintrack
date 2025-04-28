package br.com.systec.fintrack.bank.impl;


import br.com.systec.fintrack.bank.fake.BankFake;
import br.com.systec.fintrack.bank.filter.FilterBankVO;
import br.com.systec.fintrack.bank.model.Bank;
import br.com.systec.fintrack.bank.repository.BankRepository;
import br.com.systec.fintrack.bank.repository.BankRepositoryJPA;
import br.com.systec.fintrack.commons.exception.BaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class BankServiceImplTest {

    @Mock
    private BankRepositoryJPA repositoryJPA;
    @Mock
    private BankRepository repository;
    @InjectMocks
    private BankServiceImpl service;

    @Test
    void whenFindAllBank() {
        Iterable<Bank> listOfBankReturn = List.of(BankFake.toFake());

        Mockito.doReturn(listOfBankReturn).when(repository).findAll();

        Iterable<Bank> listOfBank = service.findAll();

        Assertions.assertEquals(listOfBank.spliterator().getExactSizeIfKnown(), 1);

        Mockito.verify(repository).findAll();
    }

    @Test
    void whenFindAllBankException() {
        Mockito.doThrow(new BaseException()).when(repository).findAll();

        Assertions.assertThrows(BaseException.class, () -> service.findAll());

        Mockito.verify(repository).findAll();
    }

    @Test
    void whenFindByPageAndFilter() {
        Page<Bank> listOFBankPageableReturn = new PageImpl<>(List.of(BankFake.toFake()));
        FilterBankVO filterBankVO = new FilterBankVO(30, 0, "");

        Mockito.doReturn(listOFBankPageableReturn).when(repositoryJPA).findAll(filterBankVO.getSpecification(), filterBankVO.getPageable());

        Page<Bank> listOfBankPageable = service.findByPage(filterBankVO);

        Assertions.assertNotNull(listOfBankPageable);
        Assertions.assertEquals(listOfBankPageable.getContent().size(), 1);

        Mockito.verify(repositoryJPA).findAll(filterBankVO.getSpecification(), filterBankVO.getPageable());

    }

    @Test
    void whenFindByPageAndFilterException() {
        FilterBankVO filterBankVO = new FilterBankVO(30, 0, "");

        Mockito.doThrow(new BaseException()).when(repositoryJPA).findAll(filterBankVO.getSpecification(), filterBankVO.getPageable());

        Assertions.assertThrows(BaseException.class, () -> service.findByPage(filterBankVO));

        Mockito.verify(repositoryJPA).findAll(filterBankVO.getSpecification(), filterBankVO.getPageable());
    }
}
