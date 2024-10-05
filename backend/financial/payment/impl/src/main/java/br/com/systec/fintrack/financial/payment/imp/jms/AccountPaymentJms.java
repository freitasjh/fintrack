package br.com.systec.fintrack.financial.payment.imp.jms;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsMapper;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsType;
import br.com.systec.fintrack.financial.payment.jms.AccountPaymentJmsVO;
import br.com.systec.fintrack.financial.payment.model.AccountPayment;
import br.com.systec.fintrack.financial.payment.service.AccountPaymentService;
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

import java.util.List;

@Component
public class AccountPaymentJms {
    private static final Logger log = LoggerFactory.getLogger(AccountPaymentJms.class);
    @Autowired
    private AccountPaymentService accountPaymentService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private NotificationService notificationService;


    @RabbitListener(queues = RabbitMQConstants.ACCOUNT_PAYMENT)
    @Transactional(propagation = Propagation.REQUIRED)
    public void readyJms(@Payload Message message) throws Exception {
        try {
            AccountPaymentJmsVO accountPaymentJmsVO = AccountPaymentJmsMapper.toObject(message.getPayload().toString());
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
        if (listOfPayment.isEmpty()) {
            return;
        }

//        List<User> listOfUser = userService.findAllByTenant();
//        for(User user : listOfUser){
//            saveAndSendNotification(user);
//        }
    }

    //Gera todas as notificações para os usuarios......
//    private void  saveAndSendNotification(User user) {
//        Notification notification = new Notification();
//        notification.setNotificationType(NotificationType.NOTIFICATION_PAYMENT);
//        notification.setUserId(user.getId());
//        notification.setUserEmail(user.getEmail());
//        notification.setDateCreated(new Date());
//        notification.setVisualized(false);
//        notification.setMessage("Existe pagamentos em atraso.");
//        notification.setTenantId(TenantContext.getTenant());
//
//        notificationService.save(notification);
//    }
}
