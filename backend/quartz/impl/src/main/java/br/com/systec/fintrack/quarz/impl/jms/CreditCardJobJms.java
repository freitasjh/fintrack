package br.com.systec.fintrack.quarz.impl.jms;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.creditcard.jms.CreditCardJobJmsVO;
import br.com.systec.fintrack.quartz.service.JobService;
import br.com.systec.fintrack.quartz.vo.JobVO;
import br.com.systec.fintrack.quarz.impl.job.CreditCardInvoiceJob;
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
public class CreditCardJobJms {
    private static final Logger log = LoggerFactory.getLogger(CreditCardJobJms.class);
    @Autowired
    private JobService service;

    @RabbitListener(queues = RabbitMQConstants.CREDIT_CARD_JOB)
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(@Payload Message message) {
        try {
            log.info("@@ Salvando job do cartao de credito");

            CreditCardJobJmsVO creditCardJobJmsVO = (CreditCardJobJmsVO) message.getPayload();
            TenantContext.add(creditCardJobJmsVO.getTenantId());

            if(creditCardJobJmsVO.getCreditCardJobType().equals("INSERT")) {
                insertJob(creditCardJobJmsVO);
            } else {
                updateJob(creditCardJobJmsVO);
            }
        } catch (Exception e ){
            log.error("Erro ao executar o JOB", e);
        }finally {
            TenantContext.clear();
        }
    }

    private JobVO createJob(CreditCardJobJmsVO creditCardJobJmsVO) {
        JobVO jobVO = new JobVO();
        jobVO.setCron("0 0 0 "+creditCardJobJmsVO.getCloseDay()+" * ? *");
        jobVO.setName("job_credit_card_"+creditCardJobJmsVO.getCreditCardId()+"_tt_"+creditCardJobJmsVO.getTenantId());
        jobVO.setGroup("credit_card");
        jobVO.setClassName(CreditCardInvoiceJob.class.getName());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("credit_card_id", creditCardJobJmsVO.getCreditCardId());
        jobDataMap.put("tenant_id", creditCardJobJmsVO.getTenantId());

        jobVO.setJobDataMap(jobDataMap);

        return jobVO;
    }

    private void insertJob( CreditCardJobJmsVO creditCardJobJmsVO) throws Exception {
        JobVO jobVO = createJob(creditCardJobJmsVO);

        service.save(jobVO);
    }

    private void updateJob(CreditCardJobJmsVO creditCardJobJmsVO) throws Exception {
        JobVO jobVO = createJob(creditCardJobJmsVO);

        service.deleteJob(jobVO.getName(), jobVO.getGroup());

        insertJob(creditCardJobJmsVO);
    }
}
