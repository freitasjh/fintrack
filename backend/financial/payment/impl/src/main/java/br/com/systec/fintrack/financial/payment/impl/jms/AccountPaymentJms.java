package br.com.systec.fintrack.financial.payment.impl.jms;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsType;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsVO;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
import br.com.systec.fintrack.notification.jms.NotificationJmsVO;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AccountPaymentJms {
    private static final Logger log = LoggerFactory.getLogger(AccountPaymentJms.class);

    @Autowired
    private AccountPaymentService accountPaymentService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConstants.ACCOUNT_PAYMENT)
    @Transactional(propagation = Propagation.REQUIRED)
    public void readyJms(@Payload Message message) throws Exception {
        try {
            log.info("@@@ AccountPaymentJms.readyJms({})", message.getPayload());
            AccountPaymentJmsVO accountPaymentJmsVO = (AccountPaymentJmsVO) message.getPayload();
            TenantContext.add(accountPaymentJmsVO.getTenantId());

            if (accountPaymentJmsVO.getJmsType() == AccountPaymentJmsType.PENDING) {
                findAccountPaymentPending();
            }
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar enviar as notificações de pagamento", e);
        } finally {
            TenantContext.clear();
        }
    }

    private void findAccountPaymentPending() {
        List<AccountPayment> listOfPayment = accountPaymentService.findAccountPaymentPending();

        log.info("@@@ AccountPaymentJms.findAccountPaymentPending({})", listOfPayment.size());

        if (!listOfPayment.isEmpty()) {
            NotificationJmsVO notificationJms = new NotificationJmsVO();
            notificationJms.setNotificationType(NotificationJmsVO.NotificationType.NOTIFICATION_PAYMENT);
            notificationJms.setMessage("Existe pagamentos pendentes");
            notificationJms.setTenantId(TenantContext.getTenant());

            rabbitTemplate.convertAndSend(RabbitMQConstants.NOTIFICATION, notificationJms);
        }
    }
}
