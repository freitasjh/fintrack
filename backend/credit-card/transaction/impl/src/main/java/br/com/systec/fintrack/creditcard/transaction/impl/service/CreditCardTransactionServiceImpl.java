package br.com.systec.fintrack.creditcard.transaction.impl.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.creditcard.commons.CreditCardTransactionType;
import br.com.systec.fintrack.creditcard.commons.DateCreditCardUtils;
import br.com.systec.fintrack.creditcard.exceptions.CreditCardLimitException;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.service.CreditCardService;
import br.com.systec.fintrack.creditcard.transaction.filter.CreditCardTransactionPageParam;
import br.com.systec.fintrack.creditcard.transaction.impl.repository.CreditCardTransactionRepository;
import br.com.systec.fintrack.creditcard.transaction.impl.repository.CreditCardTransactionRepositoryJPA;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardInstallment;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import br.com.systec.fintrack.creditcard.transaction.service.CreditCardTransactionService;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import br.com.systec.fintrack.invoice.service.CreditCardInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CreditCardTransactionServiceImpl implements CreditCardTransactionService {
    private static final Logger log = LoggerFactory.getLogger(CreditCardTransactionServiceImpl.class);

    @Autowired
    private CreditCardTransactionRepository repository;
    @Autowired
    private CreditCardTransactionRepositoryJPA repositoryJPA;
    @Autowired
    private CreditCardInvoiceService creditCardInvoiceService;
    @Autowired
    private CreditCardService creditCardService;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreditCardTransaction save(CreditCardTransaction creditCardTransaction) throws BaseException {
        try {
            CreditCard creditCard = creditCardService.findById(creditCardTransaction.getCreditCard().getId());

            if (creditCard.getAvailableLimit() < creditCardTransaction.getAmount()) {
                throw new CreditCardLimitException();
            }

            creditCardTransaction.setCreditCard(creditCard);

            if (creditCardTransaction.getTenantId() == null) {
                creditCardTransaction.setTenantId(TenantContext.getTenant());
            }

            if (creditCardTransaction.getDateTransaction() == null) {
                creditCardTransaction.setDateTransaction(LocalDate.now());
            }

            generateInstallments(creditCardTransaction);

            CreditCardTransaction creditCardTransactionSaved = repository.save(creditCardTransaction);

            bindInstallmentsToCreditCardInvoice(creditCardTransaction);

            //Recalcula o valor total para atualizar corretamente o valor do limite.
            double totalAmount = creditCardTransaction.getListOfInstallment().stream().filter(item -> item.getDatePaid() == null)
                    .mapToDouble(CreditCardInstallment::getAmount).sum();

            //Atualiza o limite do cartão de credito
            creditCardService.updateAvailableLimitCreditCard(totalAmount, creditCard.getId(),
                    CreditCardTransactionType.EXPENSE);

            return creditCardTransactionSaved;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar a transação", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    private void bindInstallmentsToCreditCardInvoice(CreditCardTransaction creditCardTransaction) {
        LocalDate dateToValidateInvoice = creditCardTransaction.getDateTransaction();
        for (CreditCardInstallment installment : creditCardTransaction.getListOfInstallment()) {
            LocalDate dateClose = DateCreditCardUtils.generateDateCloseWithDateTransaction(creditCardTransaction.getCreditCard(), dateToValidateInvoice);
            boolean hasInsertInvoice = false;

            if (dateClose.isBefore(LocalDate.now()) || dateClose.isEqual(LocalDate.now())) {
                hasInsertInvoice = true;
            }

            if (hasInsertInvoice) {
                creditCardInvoiceService.findByDateTransactionIfNotExistCreate(
                        dateToValidateInvoice, creditCardTransaction.getCreditCard()
                );
            }

            dateToValidateInvoice = dateToValidateInvoice.plusMonths(1);
        }
    }

    private void generateInstallments(CreditCardTransaction transaction) {
        double installmentAmount = transaction.getAmount();

        if (transaction.getInstallments() > 1) {
            installmentAmount = (transaction.getAmount() / transaction.getInstallments());
        }

        for (int index = 1; index <= transaction.getInstallments(); index++) {
            CreditCardInstallment installments = new CreditCardInstallment();
            installments.setAmount(installmentAmount);
            installments.setDescription(String.format("%s, %d/%d", transaction.getDescription(), transaction.getInstallments(), index));
            installments.setDueDate(DateCreditCardUtils.generateDueDateWithDateTransaction(transaction.getCreditCard(), index, transaction.getDateTransaction()));
            installments.setInstallment(index);
            installments.setTransaction(transaction);

            if (installments.getDueDate().isBefore(LocalDate.now())) {
                installments.setDatePaid(installments.getDueDate());
            }

            transaction.getListOfInstallment().add(installments);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PaginatedList<CreditCardTransaction> findByFilter(CreditCardTransactionPageParam pageParam) throws BaseException {
        try {
            return repository.findByFilter(pageParam);
        } catch (Exception e) {
            log.error("Erro ao tentar buscar os dados", e);
            throw new BaseException(e);
        }
    }
}
