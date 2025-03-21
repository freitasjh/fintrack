package br.com.systec.fintrack.notification.impl.jms;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.notification.jms.NotificationJmsVO;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.notification.model.NotificationType;
import br.com.systec.fintrack.notification.service.NotificationService;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class NotificationJms {
    private static final Logger log = LoggerFactory.getLogger(NotificationJms.class);

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConstants.NOTIFICATION)
    @Transactional(propagation = Propagation.REQUIRED)
    public void readyJms(@Payload Message message) {
        try {
            NotificationJmsVO notificationJms = (NotificationJmsVO) message.getPayload();
            log.info("@@@ createNotification({})", message.getPayload());

            TenantContext.add(notificationJms.getTenantId());

            Notification notification = new Notification();
            notification.setTenantId(notificationJms.getTenantId());
            notification.setNotificationType(NotificationType.valueOfCode(notificationJms.getNotificationType().getCode()));
            notification.setMessage(notificationJms.getMessage());
            notification.setDateCreated(new Date());

            notificationService.save(notification);
        } catch (Exception e) {
            log.error("Erro ao criar a notificação", e);
        } finally {
            TenantContext.clear();
        }
    }
}
