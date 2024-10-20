package br.com.systec.fintrack.financial.received.impl.jms;

import br.com.systec.fintrack.financial.received.service.AccountReceivableService;
import br.com.systec.fintrack.financial.recurringtransaction.model.RecurringTransaction;
import br.com.systec.fintrack.financial.recurringtransaction.service.RecurringTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountReceivableRecurringJms {
    private static final Logger log = LoggerFactory.getLogger(AccountReceivableRecurringJms.class);
    @Autowired
    private RecurringTransactionService recurringTransactionService;
    @Autowired
    private AccountReceivableService service;

    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(@Payload Message message) {
        try {
           log.info("@@@ Iniciando cadastro de receber recorrente..");
        } catch (Exception e){

        }
    }
}
