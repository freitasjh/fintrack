package br.com.systec.fintrack.financial.payment.impl.service;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.bankaccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ValidatorException;
import br.com.systec.fintrack.financial.payment.exceptions.InsufficientBalanceToPaymentException;
import br.com.systec.fintrack.financial.payment.exceptions.PaymentRegisterException;
import br.com.systec.fintrack.financial.payment.impl.repository.AccountPaymentRepository;
import br.com.systec.fintrack.financial.payment.impl.repository.AccountPaymentRepositoryJpa;
import br.com.systec.fintrack.financial.payment.impl.fake.AccountPaymentFake;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@SpringBootConfiguration
public class AccountPaymentServiceTest {

    @Mock
    private AccountPaymentRepository repository;
    @Mock
    private AccountPaymentRepositoryJpa repositoryJpa;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private RabbitTemplate template;

    @InjectMocks
    private AccountPaymentServiceImpl service = Mockito.spy(new AccountPaymentServiceImpl());

    @BeforeAll
    static void init() {
        TenantContext.add(1L);
    }

    @Test
    void whenSaveNewAccountPayment() {
        AccountPayment accountPaymentReturn = AccountPaymentFake.toFake();

        Mockito.when(repository.save(Mockito.any())).thenReturn(accountPaymentReturn);

        AccountPayment accountPaymentSaved = service.save(AccountPaymentFake.toFake());

        Assertions.assertThat(accountPaymentSaved).isNotNull();

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void whenSaveNewAccountPaymentAndDueDateIsNullException() {
        AccountPayment accountPaymentToSave = AccountPaymentFake.toFake();
        accountPaymentToSave.setPaymentDueDate(null);
        accountPaymentToSave.setDateProcessed(null);

        Assertions.assertThatThrownBy(() -> service.save(accountPaymentToSave))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    void whenSaveNewAccountPaymentException() {
        AccountPayment accountPaymentToSave = AccountPaymentFake.toFake();

        Mockito.when(repository.save(Mockito.any())).thenThrow(new RuntimeException());

        Assertions.assertThatThrownBy(() -> service.save(accountPaymentToSave))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void whenSaveNewAccountAndDateProcessedAndPaymentDueDateNullException() {
        AccountPayment accountPayment = AccountPaymentFake.toFake();
        accountPayment.setDateProcessed(null);
        accountPayment.setPaymentDueDate(null);

        Assertions.assertThatThrownBy(() -> service.save(accountPayment))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    void whenFindTenLastPayment() {
        List<AccountPayment> listOfAccountPaymentToReturn = List.of(AccountPaymentFake.toFake());

        Mockito.doReturn(listOfAccountPaymentToReturn).when(repository).findLastTenPayment();

        List<AccountPayment> listAccountPaymentReturn = service.findLastTenPayment();

        Assertions.assertThat(listAccountPaymentReturn).isNotNull();
        Assertions.assertThat(listAccountPaymentReturn).isNotEmpty();
        Assertions.assertThat(listAccountPaymentReturn.size()).isEqualTo(listOfAccountPaymentToReturn.size());
        Assertions.assertThat(listAccountPaymentReturn.get(0).getId()).isEqualTo(listOfAccountPaymentToReturn.get(0).getId());

        Mockito.verify(repository).findLastTenPayment();
    }

    @Test
    void whenFindAccountPaymentOpen() {
        AccountPayment accountPaymentToReturn = AccountPaymentFake.toFake();
        accountPaymentToReturn.setDateProcessed(null);
        accountPaymentToReturn.setProcessed(false);

        List<AccountPayment> listOfAccountPaymentToReturn = List.of(accountPaymentToReturn);

        Mockito.doReturn(listOfAccountPaymentToReturn).when(repository).findAccountPaymentOpen();

        List<AccountPayment> listOfAccountPaymentReturn = service.findAccountPaymentOpen();

        Assertions.assertThat(listOfAccountPaymentReturn).isNotNull();
        Assertions.assertThat(listOfAccountPaymentReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountPaymentReturn.size()).isEqualTo(listOfAccountPaymentToReturn.size());
        Assertions.assertThat(listOfAccountPaymentReturn.get(0).getId()).isEqualTo(listOfAccountPaymentToReturn.get(0).getId());
        Assertions.assertThat(listOfAccountPaymentReturn.get(0).isProcessed()).isEqualTo(listOfAccountPaymentToReturn.get(0).isProcessed());

        Mockito.verify(repository).findAccountPaymentOpen();
    }

    @Test
    void whenFindAccountPaymentOpenException() {
        Mockito.doThrow(RuntimeException.class).when(repository).findAccountPaymentOpen();

        Assertions.assertThatThrownBy(() -> service.findAccountPaymentOpen()).isInstanceOf(BaseException.class);
    }


    @Test
    void whenFindLastTenPaymentAndException() {
        Mockito.doThrow(RuntimeException.class).when(repository).findLastTenPayment();

        Assertions.assertThatThrownBy(() -> service.findLastTenPayment()).isInstanceOf(BaseException.class);
    }

    @Test
    void whenFindTotalPaymentNotProcessed() {
        Mockito.doReturn(Double.parseDouble("10")).when(repository).findTotalPaymentNotProcessed();

        Double totalReturned = service.findTotalPaymentNotProcessed();

        Assertions.assertThat(totalReturned).isEqualTo(10);

        Mockito.verify(repository).findTotalPaymentNotProcessed();
    }

    @Test
    void whenFindTotalPaymentNotProcessedException() {
        Mockito.doThrow(RuntimeException.class).when(repository).findTotalPaymentNotProcessed();

        Assertions.assertThatThrownBy(() -> service.findTotalPaymentNotProcessed()).isInstanceOf(BaseException.class);

        Mockito.verify(repository).findTotalPaymentNotProcessed();
    }

    @Test
    void whenFindAccountPaymentPending() {
        AccountPayment accountPayment = AccountPaymentFake.toFake();
        accountPayment.setProcessed(false);
        accountPayment.setDateProcessed(null);

        List<AccountPayment> listOfAccountPayment = List.of(accountPayment);

        Mockito.doReturn(listOfAccountPayment).when(repository).findAccountPaymentPending();

        List<AccountPayment> listOfAccountPaymentReturn = service.findAccountPaymentPending();

        Assertions.assertThat(listOfAccountPaymentReturn).isNotNull();
        Assertions.assertThat(listOfAccountPaymentReturn).isNotEmpty();
        Assertions.assertThat(listOfAccountPaymentReturn.size()).isEqualTo(listOfAccountPayment.size());
        Assertions.assertThat(listOfAccountPaymentReturn.get(0).getId()).isEqualTo(listOfAccountPayment.get(0).getId());


        Mockito.verify(repository).findAccountPaymentPending();
    }

    @Test
    void whenFindAccountPaymentPendingException() {
        Mockito.doThrow(RuntimeException.class).when(repository).findAccountPaymentPending();

        Assertions.assertThatThrownBy(() -> service.findAccountPaymentPending()).isInstanceOf(BaseException.class);

        Mockito.verify(repository).findAccountPaymentPending();
    }

    @Test
    void whenDeleteAccountPayment() {
        Long id = 1L;

        Mockito.doNothing().when(repository).deleteById(id);

        service.delete(id);

        Mockito.verify(repository).deleteById(id);
    }

    @Test
    void whenDeleteAccountPaymentException() {
        Long id = 1L;

        Mockito.doThrow(new RuntimeException()).when(repository).deleteById(id);

        Assertions.assertThatThrownBy(() -> service.delete(id)).isInstanceOf(BaseException.class);

        Mockito.verify(repository).deleteById(id);
    }

    @Test
    void whenFindMonthlyExpenses() {
        Mockito.doReturn(Double.parseDouble("1000")).when(repository).findMonthlyExpenses();

        Double monthlyExpenses = service.findMonthlyExpenses();

        Assertions.assertThat(monthlyExpenses).isEqualTo(1000);

        Mockito.verify(repository).findMonthlyExpenses();
    }

    @Test
    void whenFindMonthlyExpensesException() {
        Mockito.doThrow(new RuntimeException()).when(repository).findMonthlyExpenses();

        Assertions.assertThatThrownBy(() -> service.findMonthlyExpenses()).isInstanceOf(BaseException.class);

        Mockito.verify(repository).findMonthlyExpenses();
    }

    @Test
    void whenRegisterPayment() {
        AccountPayment accountPaymentToReturn = AccountPaymentFake.toFake();
        accountPaymentToReturn.setDateProcessed(null);
        accountPaymentToReturn.setProcessed(false);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(3000);

        Mockito.doReturn(Optional.of(accountPaymentToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(bankAccount).when(bankAccountService).findById(Mockito.anyLong());

        service.registerPayment(1L, new Date());

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(bankAccountService).findById(Mockito.anyLong());
        Mockito.verify(repository).update(Mockito.any());
    }

    @Test
    void whenRegisterPaymentAndAccountPaymentIsProcessed() {
        AccountPayment accountPaymentToReturn = AccountPaymentFake.toFake();
        accountPaymentToReturn.setDateProcessed(new Date());
        accountPaymentToReturn.setProcessed(true);

        Mockito.doReturn(Optional.of(accountPaymentToReturn)).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.registerPayment(1L, new Date()))
                .isInstanceOf(PaymentRegisterException.class);
    }

    @Test
    void whenRegisterPaymentAndAccountPaymentBankAccountBalanceIsInsufficient() {
        AccountPayment accountPaymentToReturn = AccountPaymentFake.toFake();
        accountPaymentToReturn.setDateProcessed(null);
        accountPaymentToReturn.setProcessed(false);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(-100);

        Mockito.doReturn(Optional.of(accountPaymentToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(bankAccount).when(bankAccountService).findById(Mockito.anyLong());


        Assertions.assertThatThrownBy(() -> service.registerPayment(1L, new Date()))
                .isInstanceOf(InsufficientBalanceToPaymentException.class);
    }

    @Test
    void whenRegisterPaymentException() {
        AccountPayment accountPaymentToReturn = AccountPaymentFake.toFake();
        accountPaymentToReturn.setDateProcessed(null);
        accountPaymentToReturn.setProcessed(false);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(3000);

        Mockito.doReturn(Optional.of(accountPaymentToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(bankAccount).when(bankAccountService).findById(Mockito.anyLong());
        Mockito.doThrow(RuntimeException.class).when(repository).update(Mockito.any(AccountPayment.class));

        Assertions.assertThatThrownBy(() -> service.registerPayment(1L, new Date()))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(bankAccountService).findById(Mockito.anyLong());
        Mockito.verify(repository).update(Mockito.any());
    }

//    @Test
//    void whenFindPaymentByFilter(){
//        List<AccountPayment> listOfAccountPayment = List.of(AccountPaymentFake.toFake());
//
//        Page<AccountPayment> pageToReturn = new PageImpl<>(listOfAccountPayment);
//
//        AccountPaymentPageParam pageParam = new AccountPaymentPageParam(30,0, null);
//        pageParam.setFilterVO(new AccountPaymentFilterVO());
//        pageParam.getFilterVO().setBankAccountId(1L);
//        pageParam.getFilterVO().setFilterType(AccountPaymentFilterType.TODOS);
//
//        Mockito.doReturn(pageToReturn).when(repositoryJpa).findAll(pageParam.getSpecification(), pageParam.getPageable());
//
//        Page<AccountPayment> pageReturn = service.findPaymentByFilter(pageParam);
//
//        Assertions.assertThat(pageReturn).isNotNull();
//        Assertions.assertThat(pageReturn).isNotEmpty();
//        Assertions.assertThat(pageReturn.getTotalElements()).isEqualTo(pageToReturn.getTotalElements());
//        Assertions.assertThat(pageReturn.getContent().get(0).getId()).isEqualTo(pageToReturn.getContent().get(0).getId());
//
//        Mockito.verify(repositoryJpa).findAll(pageParam.getSpecification(), pageParam.getPageable());
//
//    }

}
