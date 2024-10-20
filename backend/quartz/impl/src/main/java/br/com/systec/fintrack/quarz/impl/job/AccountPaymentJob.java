package br.com.systec.fintrack.quarz.impl.job;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.TenantNotFoundException;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsType;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsVO;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountPaymentJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AccountPaymentJob.class);
    @Autowired
    private RabbitTemplate template;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Long tenantId = (Long) context.getJobDetail().getJobDataMap().get("tenantId");

            if (tenantId == null) {
                throw new TenantNotFoundException("Sem tenant cadastrado no job, entre em contato com o suporte");
            }

            AccountPaymentJmsVO jmsVO = new AccountPaymentJmsVO();
            jmsVO.setJmsType(AccountPaymentJmsType.PENDING);
            jmsVO.setTenantId(tenantId);

            template.convertAndSend(RabbitMQConstants.ACCOUNT_PAYMENT, jmsVO);
        } catch (Exception e) {
            log.error("Erro ao tentar enviar criar a mensagem para a fila de pagamento, Tenant {}", TenantContext.getTenant(), e);
        }
    }
}
