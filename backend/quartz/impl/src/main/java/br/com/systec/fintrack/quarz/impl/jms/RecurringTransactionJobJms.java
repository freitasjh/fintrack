package br.com.systec.fintrack.quarz.impl.jms;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.creditcard.jms.CreditCardJobJmsVO;
import br.com.systec.fintrack.financial.recurringtransaction.jms.RecurringTransactionJobVO;
import br.com.systec.fintrack.quartz.service.JobService;
import br.com.systec.fintrack.quartz.vo.JobVO;
import br.com.systec.fintrack.quarz.impl.job.CreditCardInvoiceJob;
import br.com.systec.fintrack.quarz.impl.job.RecurringTransactionJob;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RecurringTransactionJobJms {
    private static final Logger log = LoggerFactory.getLogger(RecurringTransactionJobJms.class);
    public static final String TENANT_ID = "tenant_id";
    public static final String TRANSACTION_TYPE_CODE = "transaction_type_code";
    public static final String RECURRING_TRANSACTION_ID = "recurring_transaction_id";

    @Autowired
    private JobService service;

    @RabbitListener(queues = RabbitMQConstants.RECURRING_FINANCIAL_JOB)
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(@Payload Message message) {
        try {
            log.info("@@ Salvando Job de Transação recorrente...");
            RecurringTransactionJobVO recurringTransactionJobVO = (RecurringTransactionJobVO) message.getPayload();
            TenantContext.add(recurringTransactionJobVO.getTenantId());

            insertJob(recurringTransactionJobVO); //TODO fazer o de deletar.
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar criar o job de recorrente");
        } finally {
            TenantContext.clear();
        }
    }

    private JobVO createJob(RecurringTransactionJobVO recurringTransactionJobVO) {
        JobVO jobVO = new JobVO();
        jobVO.setCron(recurringTransactionJobVO.getCronJob());
        jobVO.setName("job_recurring_transaction_"+recurringTransactionJobVO.getRecurringTransactionId()+"_tt_"+recurringTransactionJobVO.getTenantId());
        jobVO.setGroup("recurring_transaction");
        jobVO.setClassName(RecurringTransactionJob.class.getName());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(TENANT_ID, recurringTransactionJobVO.getTenantId());
        jobDataMap.put(TRANSACTION_TYPE_CODE, recurringTransactionJobVO.getTransactionTypeCode());
        jobDataMap.put(RECURRING_TRANSACTION_ID, recurringTransactionJobVO.getRecurringTransactionId());

        jobVO.setJobDataMap(jobDataMap);

        return jobVO;
    }

    private void insertJob( RecurringTransactionJobVO recurringTransactionJobVO) throws Exception {
        JobVO jobVO = createJob(recurringTransactionJobVO);

        service.save(jobVO);
    }

    private void updateJob(RecurringTransactionJobVO recurringTransactionJobVO) throws Exception {
        JobVO jobVO = createJob(recurringTransactionJobVO);

        service.deleteJob(jobVO.getName(), jobVO.getGroup());

        insertJob(recurringTransactionJobVO);
    }
}
