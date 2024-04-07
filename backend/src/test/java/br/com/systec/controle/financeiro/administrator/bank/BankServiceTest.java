package br.com.systec.controle.financeiro.administrator.bank;

import br.com.systec.controle.financeiro.administrator.bank.filter.FilterBankVO;
import br.com.systec.controle.financeiro.administrator.bank.model.Bank;
import br.com.systec.controle.financeiro.administrator.bank.repository.BankRepository;
import br.com.systec.controle.financeiro.administrator.bank.repository.BankRepositoryJPA;
import br.com.systec.controle.financeiro.administrator.bank.service.BankService;
import br.com.systec.controle.financeiro.fake.BankFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@SpringBootTest
public class BankServiceTest {

    @Mock
    private BankRepositoryJPA repositoryJPA;
    @Mock
    private BankRepository repository;
    @InjectMocks
    private BankService service;

    @Test
    void whenFindAllBank() {
        Iterable<Bank> listOfBankReturn = List.of(BankFake.toFake());

        Mockito.doReturn(listOfBankReturn).when(repository).findAll();

        Iterable<Bank> listOfBank = service.findAll();

        Assertions.assertEquals(listOfBank.spliterator().getExactSizeIfKnown(), 1);

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
}
