package br.com.systec.fintrack.quarz.impl.job;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.TenantNotFoundException;
import br.com.systec.fintrack.invoice.jms.CreditCardInvoiceJmsVO;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class CreditCardInvoicePendingJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoicePendingJob.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Long tenantId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get("tenant_id");
            Long creditCardId = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get("credit_card_id");

            if (tenantId == null) {
                throw new TenantNotFoundException("Sem tenant cadastrado no job");
            }

            if (creditCardId == null) {
                throw new BaseException("Sem id de cartao de credito cadastrado");
            }

            CreditCardInvoiceJmsVO jmsVO = new CreditCardInvoiceJmsVO();
            jmsVO.setCreditCardId(creditCardId);
            jmsVO.setTenantId(tenantId);

            rabbitTemplate.convertAndSend(RabbitMQConstants.CREDIT_CARD_INVOICE_PENDING, jmsVO);

        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar executar o job de verificação de pendencias", e);
        }
    }
}
