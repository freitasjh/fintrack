package br.com.systec.fintrack.creditcard.invoice.impl.service;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.bankAccount.service.BankAccountService;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.commons.utils.DateUtils;
import br.com.systec.fintrack.creditcard.commons.CreditCardTransactionType;
import br.com.systec.fintrack.creditcard.commons.DateCreditCardUtils;
import br.com.systec.fintrack.creditcard.invoice.impl.repository.CreditCardInvoiceRepository;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.service.CreditCardService;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardInstallment;
import br.com.systec.fintrack.creditcard.transaction.service.CreditCardInstallmentService;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import br.com.systec.fintrack.financial.transaction.model.CategoryTransactionType;
import br.com.systec.fintrack.invoice.vo.CreditCardFutureInvoiceVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInstallmentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoicePaymentVO;
import br.com.systec.fintrack.invoice.vo.CreditCardInvoiceResultVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.model.InvoiceStatusType;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import br.com.systec.fintrack.invoice.vo.filter.CreditCardInvoiceFilterParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CreditCardInvoiceServiceImpl implements CreditCardInvoiceService {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoiceServiceImpl.class);

    @Autowired
    private CreditCardInvoiceRepository repository;
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private CreditCardInstallmentService installmentService;
    @Autowired
    private AccountPaymentService accountPaymentService;
    @Autowired
    private BankAccountService bankAccountService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCardInvoice findByDateIfNotExistCreate(CreditCard creditCard) throws BaseException {
        CreditCardInvoice creditCardInvoice = repository.findByDateClose(LocalDate.now(), creditCard.getId(), LocalDate.now())
                .orElse(null);

        if (creditCardInvoice == null) {
            return create(creditCard, LocalDate.now());
        }

        return creditCardInvoice;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCardInvoice findByDateTransactionIfNotExistCreate(LocalDate dateTransaction, CreditCard creditCard) throws BaseException {

        LocalDate dateClose = DateCreditCardUtils.generateDateCloseWithDateTransaction(creditCard, dateTransaction);
        CreditCardInvoice creditCardInvoice = repository.findByDateClose(dateTransaction, creditCard.getId(), dateClose)
                .orElse(null);

        if (creditCardInvoice == null) {
            return create(creditCard, dateTransaction);
        }

        installmentService.bindInstallmentsToInvoiceIdCreditCard(creditCardInvoice.getId(),
                creditCardInvoice.getCreditCard().getId(), creditCardInvoice.getDueDate());

        return creditCardInvoice;
    }


    private CreditCardInvoice create(CreditCard creditCard, LocalDate dateTransaction) throws BaseException {
        try {
            creditCard = creditCardService.findById(creditCard.getId());
            CreditCardInvoice creditCardInvoice = new CreditCardInvoice();
            creditCardInvoice.setCreditCard(creditCard);

            creditCardInvoice.setDateClose(DateCreditCardUtils.generateDateCloseWithDateTransaction(creditCard, dateTransaction));

            creditCardInvoice.setDueDate(DateCreditCardUtils.generateDueDateWithDateTransaction(creditCard,1, dateTransaction));
            creditCardInvoice.setTenantId(TenantContext.getTenant());
            creditCardInvoice.setStatusType(InvoiceStatusType.OPEN);
            if(creditCardInvoice.getDueDate().isBefore(LocalDate.now())){
                creditCardInvoice.setStatusType(InvoiceStatusType.PAID);
            }

            CreditCardInvoice creditCardInvoiceAfterSave = repository.save(creditCardInvoice);

            //Atualiza as parcelas existentes do cartão de credito com a nova fatura gerada.
            installmentService.bindInstallmentsToInvoiceIdCreditCard(creditCardInvoiceAfterSave.getId(),
                    creditCardInvoiceAfterSave.getCreditCard().getId(), creditCardInvoiceAfterSave.getDueDate());

            return creditCardInvoiceAfterSave;
        } catch (Exception e) {
            log.error("Erro ao tentar salvar a fatura", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PaginatedList<CreditCardInvoiceResultVO> findByFilter(CreditCardInvoiceFilterParam filterVO) throws BaseException {
        return repository.findInvoiceByFilter(filterVO);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardFutureInvoiceVO> findFutureInvoiceByFilter() throws BaseException {
        return repository.findAllFutureInvoice(LocalDate.now());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Double findAmountInvoiceOpen() throws BaseException {
        return repository.findAmountInvoiceOpen();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void paymentInvoice(CreditCardInvoicePaymentVO invoicePayment) throws BaseException {
        CreditCardInvoiceResultVO creditCardInvoice = repository.findCreditCardInvoiceById(invoicePayment.getCreditCardInvoiceId()).orElseThrow();
        CreditCardInvoice creditCardInvoiceFull = repository.findById(invoicePayment.getCreditCardInvoiceId()).orElseThrow();
        BankAccount bankAccount = bankAccountService.findById(invoicePayment.getBankAccountId());

        if(creditCardInvoiceFull.getDatePaid() != null) {
            throw new BaseException("Já foi registrado o pagamento da fatura");
        }

        if(invoicePayment.getAmount() < creditCardInvoice.amount()){
            throw new BaseException("Informe um valor igual ao da fatura");
        }

        if(bankAccount.getBalance() < invoicePayment.getAmount()){
            throw new BaseException("Saldo insuficiente na conta");
        }

        repository.creditCardInvoicePay(invoicePayment.getCreditCardInvoiceId());

        installmentService.registerPaymentInstallmentsForInvoiceId(invoicePayment.getCreditCardInvoiceId(), invoicePayment.getDatePay());

        creditCardService.updateAvailableLimitCreditCard(invoicePayment.getAmount(), creditCardInvoiceFull.getCreditCard().getId(),
                CreditCardTransactionType.PAYMENT);

        createAccountPaymentInvoicePaid(invoicePayment, creditCardInvoiceFull);
    }

    private void createAccountPaymentInvoicePaid(CreditCardInvoicePaymentVO invoicePaymentVO, CreditCardInvoice invoice) {
        AccountPayment accountPayment = new AccountPayment();
        accountPayment.setAmount(invoicePaymentVO.getAmount());
        accountPayment.setDateProcessed(DateUtils.converterLocalDateToDate(invoicePaymentVO.getDatePay()));
        accountPayment.setPaymentDueDate(DateUtils.converterLocalDateToDate(invoice.getDueDate()));
        accountPayment.setDateRegister(new Date());
        accountPayment.setTransactionType(TransactionType.EXPENSE);
        accountPayment.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        accountPayment.setTenantId(TenantContext.getTenant());
        accountPayment.setDescription("Pagamento da fatura do dia "+invoice.getDueDate()+".: Do cartão "+invoice.getCreditCard().getName());
        accountPayment.setBankAccount(new BankAccount(invoicePaymentVO.getBankAccountId()));
        accountPayment.setProcessed(true);

        accountPaymentService.save(accountPayment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void validateAndMarkInvoiceAsPending(Long creditCardId) throws BaseException {
        log.info("Iniciando a validação de contas pendentes.");
        List<CreditCardInvoice> listOfCreditCardInvoice = repository.findInvoicePendingPayment(creditCardId);

        if (listOfCreditCardInvoice.isEmpty()) {
            log.info("Não foi encontrado contas pendentes para o tenant {}.", TenantContext.getTenant());
            return;
        }

        for (CreditCardInvoice item : listOfCreditCardInvoice) {
            log.info("Marcando a fatura {} como pendente.", item.getId());
            item.setStatusType(InvoiceStatusType.PENDING);
            repository.update(item);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CreditCardInstallmentVO> findInstallmentByCreditCardInvoiceId(Long creditCardInvoiceId) throws BaseException {
        List<CreditCardInstallment> listOfInstallment = installmentService.findInstallmentByCreditCardInvoiceId(creditCardInvoiceId);
        List<CreditCardInstallmentVO> listOfInstallmentVO = new ArrayList<>(listOfInstallment.size());

        for(CreditCardInstallment item : listOfInstallment){
            CreditCardInstallmentVO creditCardInvoiceResult = new CreditCardInstallmentVO.Builder().
                    description(item.getDescription())
                    .amount(item.getAmount())
                    .dateCreate(item.getDateCreate())
                    .installment(item.getInstallment())
                    .build();

            listOfInstallmentVO.add(creditCardInvoiceResult);
        }

        return listOfInstallmentVO;
    }
}
