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

public class CreditCardInvoiceJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoiceJob.class);
    @Autowired
    private RabbitTemplate template;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Long tenantId = (Long) context.getJobDetail().getJobDataMap().get("tenant_id");
            Long creditCardId = (Long) context.getJobDetail().getJobDataMap().get("credit_card_id");

            if(tenantId == null){
                throw new TenantNotFoundException("Sem tenant cadastrado no job");
            }

            if(creditCardId == null) {
                throw new BaseException("Sem id de cartão de credito cadastrado");
            }

            CreditCardInvoiceJmsVO invoiceJms = new CreditCardInvoiceJmsVO();
            invoiceJms.setTenantId(tenantId);
            invoiceJms.setCreditCardId(creditCardId);

            template.convertAndSend(RabbitMQConstants.CREDIT_CARD_NEW_INVOICE, invoiceJms);

        } catch (Exception e) {
            log.error("Erro ao tentar enviar criar a mensagem para a fila de nova fatura.", e);
        }
    }
}
