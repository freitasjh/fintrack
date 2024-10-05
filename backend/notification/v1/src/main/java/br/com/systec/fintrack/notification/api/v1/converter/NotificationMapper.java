package br.com.systec.fintrack.notification.api.v1.converter;

import br.com.systec.fintrack.notification.api.v1.dto.NotificationDTO;
import br.com.systec.fintrack.notification.api.v1.dto.NotificationInputDTO;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.notification.model.NotificationType;

import java.util.Date;

public class NotificationMapper {

    public static Notification toEntity(NotificationInputDTO inputDTO) {
        Notification notification = new Notification();
        notification.setId(inputDTO.getId());
        notification.setMessage(inputDTO.getMessage());
        notification.setDateCreated(new Date());
        notification.setNotificationType(NotificationType.valueOfCode(inputDTO.getNotificationTypeCode()));
        notification.setUserId(inputDTO.getUserId());

        return notification;
    }

    public static NotificationDTO toDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setVisualized(notification.isVisualized());
        notificationDTO.setDateVisualized(notification.getDateVisualized());
        notificationDTO.setDateCreated(notification.getDateCreated());

        return notificationDTO;
    }


}
