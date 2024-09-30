package br.com.systec.fintrack.financial.accountTransfer.service;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.fake.BankAccountFake;
import br.com.systec.fintrack.financial.accountPayment.model.AccountPayment;
import br.com.systec.fintrack.financial.accountPayment.service.AccountPaymentService;
import br.com.systec.fintrack.financial.accountReceivable.model.AccountReceivable;
import br.com.systec.fintrack.financial.accountReceivable.service.AccountReceivableService;
import br.com.systec.fintrack.financial.accountTransfer.fake.AccountTransferFake;
import br.com.systec.fintrack.financial.accountTransfer.filter.AccountTransferFilterVO;
import br.com.systec.fintrack.financial.accountTransfer.model.AccountTransfer;
import br.com.systec.fintrack.financial.accountTransfer.repository.AccountTransferRepository;
import br.com.systec.fintrack.financial.accountTransfer.repository.AccountTransferRepositoryJPA;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class AccountTransferServiceTest {
    @Mock
    private AccountTransferRepository repository;
    @Mock
    private AccountTransferRepositoryJPA repositoryJPA;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private AccountPaymentService accountPaymentService;
    @Mock
    private AccountReceivableService accountReceivableService;
    @InjectMocks
    private AccountTransferService service;

    @Test
    void whenSaveAccountTransfer() {
        TenantContext.add(1L);
        BankAccount bankAccountTo = BankAccountFake.fake();
        BankAccount bankAccountFrom = BankAccountFake.fake();

        bankAccountFrom.setId(2L);
        bankAccountFrom.setDescription("Banco teste 2");
        bankAccountFrom.setBalance(100.00);

        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setTransferDate(new Date());
        accountTransfer.setBankAccountFrom(bankAccountFrom);
        accountTransfer.setBankAccountTo(bankAccountTo);
        accountTransfer.setAmount(60.00);
        accountTransfer.setDescription("Transferencia teste");
        accountTransfer.setTenantId(1L);

        Mockito.doReturn(accountTransfer).when(repository).save(Mockito.any(AccountTransfer.class));
        Mockito.doReturn(bankAccountTo).when(bankAccountService).findById(1L);
        Mockito.doReturn(bankAccountFrom).when(bankAccountService).findById(2L);

        AccountTransfer accountTransferSavedReturn = service.save(accountTransfer);

        Assertions.assertThat(accountTransferSavedReturn).isNotNull();

        Mockito.verify(repository).save(Mockito.any(AccountTransfer.class));
        Mockito.verify(accountPaymentService).save(Mockito.any(AccountPayment.class));
        Mockito.verify(accountReceivableService).save(Mockito.any(AccountReceivable.class));
        Mockito.verify(bankAccountService, Mockito.times(2)).findById(Mockito.anyLong());
    }

    @Test
    void whenBankAccountFromInsufficientFunds() {
        TenantContext.add(1L);
        BankAccount bankAccountTo = BankAccountFake.fake();
        BankAccount bankAccountFrom = BankAccountFake.fake();

        bankAccountFrom.setId(2L);
        bankAccountFrom.setDescription("Banco teste 2");
        bankAccountFrom.setBalance(100.00);

        AccountTransfer accountTransfer = new AccountTransfer();
        accountTransfer.setTransferDate(new Date());
        accountTransfer.setBankAccountFrom(bankAccountFrom);
        accountTransfer.setBankAccountTo(bankAccountTo);
        accountTransfer.setAmount(110.00);
        accountTransfer.setDescription("Transferencia teste");
        accountTransfer.setTenantId(1L);

        Mockito.doReturn(bankAccountTo).when(bankAccountService).findById(1L);
        Mockito.doReturn(bankAccountFrom).when(bankAccountService).findById(2L);

        Assertions.assertThatThrownBy(() -> service.save(accountTransfer))
                .isInstanceOf(BaseException.class)
                .hasMessage("Saldo insuficiente para realizar a transferencia");

        Mockito.verify(bankAccountService, Mockito.times(1))
                .findById(Mockito.anyLong());
    }

    @Test
    void whenFindAllAccountTransfer() {
        TenantContext.add(1L);
        List<AccountTransfer> listOfAccountTransferReturn = List.of(AccountTransferFake.toFake());

        Mockito.doReturn(listOfAccountTransferReturn).when(repository).findAllByTenant();

        List<AccountTransfer> listOfAccountTransfer = service.findAll();

        Assertions.assertThat(listOfAccountTransfer).isNotNull();
        Assertions.assertThat(listOfAccountTransfer).isNotEmpty();
        Assertions.assertThat(listOfAccountTransfer.size()).isEqualTo(listOfAccountTransferReturn.size());
        Assertions.assertThat(listOfAccountTransfer.get(0).getId()).isEqualTo(listOfAccountTransferReturn.get(0).getId());

        Mockito.verify(repository).findAllByTenant();
    }

    @Test
    void whenFindAccountTransferByFilterByTenant() {
        TenantContext.add(1L);

        Page<AccountTransfer> pageReturn = new PageImpl(List.of(AccountTransferFake.toFake()));

        AccountTransferFilterVO filterVO = new AccountTransferFilterVO(30, 0, "");

        Mockito.doReturn(pageReturn).when(repositoryJPA).findAll(filterVO.getSpecification(), filterVO.getPageable());

        Page<AccountTransfer> pageReturnFind = service.findByFilter(filterVO);

        Assertions.assertThat(pageReturnFind).isNotNull();
        Assertions.assertThat(pageReturnFind.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(pageReturnFind.getContent()).isNotEmpty();

        Mockito.verify(repositoryJPA).findAll(filterVO.getSpecification(), filterVO.getPageable());
    }
}
