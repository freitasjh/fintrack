package br.com.systec.fintrack.financial.received.sdk.service;

import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import br.com.systec.fintrack.financial.received.vo.AccountReceivableVO;
import br.com.systec.fintrack.financial.recurringtransaction.model.FrequencyType;
import br.com.systec.fintrack.financial.recurringtransaction.service.RecurringTransactionService;
import br.com.systec.fintrack.financial.recurringtransaction.vo.RecurringTransactionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountReceivableSdkService {
    private static final Logger log = LoggerFactory.getLogger(AccountReceivableSdkService.class);

    @Autowired
    private AccountReceivableService accountReceivableService;
    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void recurringTransactionReceiver(Long recurringId) {
        log.info("@@@ Iniciando inserir conta recorrente");

        RecurringTransactionVO recurringTransaction = recurringTransactionService.findByIdAndTenant(recurringId);

        AccountReceivableVO accountReceivable = accountReceivableService.findById(recurringTransaction.getTransactionId());
        Optional<AccountReceivableVO> accountReceivableNew = Optional.empty();

        if (recurringTransaction.getFrequencyType() == FrequencyType.MONTHLY) {
            accountReceivableNew = createNewAccountReceivableMouth(accountReceivable);
        }

        accountReceivableNew.ifPresent(accountReceivableVO -> accountReceivableService.save(accountReceivableVO));
    }

    private Optional<AccountReceivableVO> createNewAccountReceivableMouth(AccountReceivableVO accountReceivableRecurring) {
        LocalDate currentDate = LocalDate.now();
        LocalDate accountReceivableDateRegister = accountReceivableRecurring.getDateRegister().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        TemporalQuery<Boolean> sameMonthAndYearQuery = temporal -> {
            LocalDate otherDate = LocalDate.from(temporal);
            return currentDate.getYear() == otherDate.getYear() && currentDate.getMonth() == otherDate.getMonth();
        };

        boolean isSameMouthAndYear = accountReceivableDateRegister.query(sameMonthAndYearQuery);

        if (isSameMouthAndYear) {
            log.info("@@@ Já existe uma conta recebida para o mês corrente");
            return Optional.empty();
        }

        LocalDate newDateRegister = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), accountReceivableDateRegister.getDayOfMonth());

        AccountReceivableVO accountReceivableNew = createAccountReceivable(accountReceivableRecurring, newDateRegister, true);

        return Optional.of(accountReceivableNew);
    }

    private AccountReceivableVO createAccountReceivable(AccountReceivableVO accountReceivableRecurring,
                                                        LocalDate newDateRegister, boolean hasProcessed) {
        AccountReceivableVO accountReceivableNew = new AccountReceivableVO();
        accountReceivableNew.setBankAccount(accountReceivableRecurring.getBankAccount());
        accountReceivableNew.setTransactionType(accountReceivableRecurring.getTransactionType());
        accountReceivableNew.setDateRegister(Date.from(newDateRegister.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        accountReceivableNew.setCategoryTransactionType(accountReceivableRecurring.getCategoryTransactionType());
        accountReceivableNew.setAmount(accountReceivableRecurring.getAmount());
        accountReceivableNew.setDescription(accountReceivableRecurring.getDescription()+" / "+newDateRegister.getMonthValue()+"-"+newDateRegister.getYear());
        accountReceivableNew.setProcessed(hasProcessed);
        accountReceivableNew.setTenantId(accountReceivableRecurring.getTenantId());

        if (hasProcessed) {
            accountReceivableNew.setDateProcessed(new Date());
        }

        return accountReceivableNew;
    }
}
