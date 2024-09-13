package br.com.systec.controle.financeiro.quartz.jobs;

import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.financial.accountPayment.jms.AccountPaymentJmsMapper;
import br.com.systec.controle.financeiro.financial.accountPayment.jms.AccountPaymentJmsType;
import br.com.systec.controle.financeiro.financial.accountPayment.jms.AccountPaymentJmsVO;
import br.com.systec.controle.financeiro.financial.accountPayment.model.AccountPayment;
import br.com.systec.controle.financeiro.financial.accountPayment.service.AccountPaymentService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountPaymentJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AccountPaymentJob.class);
    @Autowired
    private RabbitTemplate template;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Long tenantId = (Long) context.getJobDetail().getJobDataMap().get("tenantId");
            log.warn("Testando tenantId {}", tenantId);
            if(tenantId == null){
                throw new BaseException("Erro sem tenant cadastrado no job");
            }
            AccountPaymentJmsVO jmsVO = new AccountPaymentJmsVO();
            jmsVO.setJmsType(AccountPaymentJmsType.PENDING);
            jmsVO.setTenantId(tenantId);

            template.convertAndSend(RabbitMQConfig.ACCOUNT_PAYMENT, AccountPaymentJmsMapper.toJson(jmsVO));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
