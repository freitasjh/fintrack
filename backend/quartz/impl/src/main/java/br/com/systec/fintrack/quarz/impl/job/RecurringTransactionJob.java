package br.com.systec.fintrack.quarz.impl.job;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.TenantNotFoundException;
import br.com.systec.fintrack.commons.model.TransactionType;
import br.com.systec.fintrack.financial.received.sdk.service.AccountReceivableSdkService;
import br.com.systec.fintrack.financial.recurringtransaction.jms.RecurringTransactionJobVO;
import br.com.systec.fintrack.quarz.impl.jms.RecurringTransactionJobJms;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecurringTransactionJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(RecurringTransactionJobVO.class);

    @Autowired
    private AccountReceivableSdkService accountReceivableService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Long tenantId = (Long) context.getJobDetail().getJobDataMap().get(RecurringTransactionJobJms.TENANT_ID);
            Long recurringTransactionId = (Long) context.getJobDetail().getJobDataMap().get(RecurringTransactionJobJms.RECURRING_TRANSACTION_ID);
            int transactionTypeCode = (int) context.getJobDetail().getJobDataMap().get(RecurringTransactionJobJms.TRANSACTION_TYPE_CODE);

            if (tenantId == null) {
                throw new TenantNotFoundException("Sem tenant cadastrado no job, entre em contato com o suporte");
            }
            TenantContext.add(tenantId);

            log.info("@@@ Iniciando o job do tenant, {}, Transaction_id, {}", tenantId, recurringTransactionId);

            TransactionType transactionType = TransactionType.valueOfCode(transactionTypeCode);

            if (transactionType == TransactionType.INCOMING) {
                log.info("@@@ Iniciando excecucao de Recebidos recorrente");
                accountReceivableService.recurringTransactionReceiver(recurringTransactionId);
            } else if (transactionType == TransactionType.EXPENSE) {
                log.info("@@@ Iniciar excecucao de Pagar recorrente");
            }
        }finally {
            TenantContext.clear();
        }
    }
}
