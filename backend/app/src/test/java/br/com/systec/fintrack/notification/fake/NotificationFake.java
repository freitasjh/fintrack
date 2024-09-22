package br.com.systec.fintrack.notification.fake;

import br.com.systec.fintrack.notification.api.v1.dto.NotificationInputDTO;
import br.com.systec.fintrack.notification.enums.NotificationType;
import br.com.systec.fintrack.notification.model.Notification;

import java.util.Date;

public class NotificationFake {

    public static Notification toFake() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setTenantId(1L);
        notification.setNotificationType(NotificationType.NOTIFICATION_PAYMENT);
        notification.setMessage("Teste de notificacao");
        notification.setUserId(1L);
        notification.setUserEmail("freitasjh");
        notification.setVisualized(false);
        notification.setDateCreated(new Date());

        return notification;
    }

    public static NotificationInputDTO toFakeInputDTO() {
        NotificationInputDTO notification = new NotificationInputDTO();
        notification.setId(1L);
        notification.setTenantId(1L);
        notification.setNotificationTypeCode(1);
        notification.setMessage("Teste de notificacao");
        notification.setUserId(1L);

        return notification;
    }
}
