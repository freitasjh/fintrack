package br.com.systec.fintrack.financial.payment.impl.service;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.commons.exception.ValidatorException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.payment.filter.AccountPaymentPageParam;
import br.com.systec.fintrack.financial.payment.impl.repository.AccountPaymentRepository;
import br.com.systec.fintrack.financial.payment.impl.repository.AccountPaymentRepositoryJpa;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountPaymentServiceImpl implements AccountPaymentService {
    private static final Logger log = LoggerFactory.getLogger(AccountPayment.class);

    @Autowired
    private AccountPaymentRepository repository;
    @Autowired
    private AccountPaymentRepositoryJpa repositoryJpa;
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private BankAccountService bankAccountService;

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountPayment save(AccountPayment accountPayment) throws BaseException {
        try {
            if (accountPayment.getDateProcessed() == null && accountPayment.getPaymentDueDate() == null) {
                throw new ValidatorException("Informe a data de pagamento ou a data de vencimento");
            }

            AccountPayment accountPaymentSaved = repository.save(accountPayment);

            if (accountPayment.getDateProcessed() != null) {
                updateBalanceAccountBank(accountPayment);
            }

            return accountPaymentSaved;
        } catch (BaseException e) {
            log.error("Ocorreu um erro ao tentar salvar o pagamento", e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar slavar o pagamento", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar o pagamento", e);
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AccountPayment> listAllPayment() throws BaseException {
        return repository.findAllByTenant();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<AccountPayment> findPaymentByFilter(AccountPaymentPageParam pageParam) throws BaseException {
        try {
            Page<AccountPayment> pageOfAccountPayment = repositoryJpa.findAll(pageParam.getSpecification(),
                    pageParam.getPageable());

            return pageOfAccountPayment;
        } catch (Exception e) {
            log.error("Erro ao tentar filtar os pagamento", e);
            throw new BaseException("Erro ao tentar filtar os pagamentos", e);
        }

    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findMonthlyExpenses() throws BaseException {
        try {
            return repository.findMonthlyExpenses();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar as despesas mensais", e);
            throw new BaseException("Ocorreu um erro ao tentar pesquisar as despesas mensais", e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountPayment> findLastTenPayment() throws BaseException {
        try {
            return repository.findLastTenPayment();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar os ultimos pagamentos", e);
            throw new BaseException("Ocorreu um erro ao tentar pesquisar os ultimos pagamentos", e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findTotalPaymentNotProcessed() throws BaseException {
        try {
            return repository.findTotalPaymentNotProcessed();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquiar os pagamanetos não processados", e);
            throw new BaseException(e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AccountPayment> findAccountPaymentOpen() throws BaseException {
        try {
            return repository.findAccountPaymentOpen();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar os pagamentos em aberto", e);
            throw new BaseException("Ocorreu um erro ao tentar pesquisar os pagamentos em aberto", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void registerPayment(Long paymentId, Date dateRegister) throws BaseException {
        try {
            AccountPayment accountPayment = repository.findById(paymentId)
                    .orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada"));

            if (accountPayment.isProcessed()) {
                throw new BaseException("Pagamento já foi registrado");
            }

            BankAccount bankAccount = bankAccountService.findById(accountPayment.getBankAccount().getId());

            if (bankAccount.getBalance() < accountPayment.getAmount()) {
                throw new BaseException("Saldo insuficiente para realizar o pagamento");
            }

            accountPayment.setDateProcessed(dateRegister);
            accountPayment.setProcessed(true);

            repository.update(accountPayment);
            updateBalanceAccountBank(accountPayment);

        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar processar o pagamento", e);
            throw new BaseException("Ocorreu um erro ao tentar processar o pagamento", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AccountPayment> findAccountPaymentPending() throws BaseException {
        try {
            return repository.findAccountPaymentPending();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar os pagamentos pendentes", e);
            throw new BaseException("Ocorreu um erro ao tentar pesquisar os pagamentos pendentes", e);
        }
    }


    private void updateBalanceAccountBank(AccountPayment accountPayment) throws BaseException {
        bankAccountService.updateBankAccountBalance(accountPayment.getAmount(), accountPayment.getBankAccount().getId(), TransactionType.EXPENSE);
    }

    //TODO fazer essa logica quando tirar as responsabilidades de chamar o service da conta bancaria.
//    private void convertAndSendJmsBankAccount(AccountPayment accountPayment) throws BaseException {
//        BankAccountJms bankAccountJms = new BankAccountJms(accountPayment.getTenantId(),
//                accountPayment.getBankAccount().getId(), accountPayment.getAmount(), TransactionType.EXPENSE);
//
//        template.convertAndSend(RabbitMQConstants.FINANCIAL_EXCHANGE,
//                RabbitMQConstants.ROUTING_KEY_NEW_BALANCE_ACCOUNT, bankAccountJms);
//    }
}
