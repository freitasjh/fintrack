package br.com.systec.controle.financeiro.notification.api.v1.mapper;

import br.com.systec.controle.financeiro.notification.api.v1.dto.NotificationDTO;
import br.com.systec.controle.financeiro.notification.api.v1.dto.NotificationInputDTO;
import br.com.systec.controle.financeiro.notification.fake.NotificationFake;
import br.com.systec.controle.financeiro.notification.model.Notification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class NotificationMapperTest {

    @Test
    void whenConverterNotificationInputDTOToNotification() {
        NotificationInputDTO inputDTO = NotificationFake.toFakeInputDTO();

        Notification notificationConverted = NotificationMapper.toEntity(inputDTO);

        Assertions.assertThat(notificationConverted).isNotNull();
        Assertions.assertThat(notificationConverted.getId()).isEqualTo(inputDTO.getId());
        Assertions.assertThat(notificationConverted.getMessage()).isEqualTo(inputDTO.getMessage());
        Assertions.assertThat(notificationConverted.getUserId()).isEqualTo(inputDTO.getUserId());
        Assertions.assertThat(notificationConverted.getNotificationType().getCode()).isEqualTo(inputDTO.getNotificationTypeCode());
    }

    @Test
    void whenConvertedNotificationToNotificationDTO() {
        Notification notification = NotificationFake.toFake();

        NotificationDTO notificationConverted = NotificationMapper.toDTO(notification);

        Assertions.assertThat(notificationConverted).isNotNull();
        Assertions.assertThat(notificationConverted.getId()).isEqualTo(notification.getId());
        Assertions.assertThat(notificationConverted.getMessage()).isEqualTo(notification.getMessage());
        Assertions.assertThat(notificationConverted.getDateCreated()).isEqualTo(notification.getDateCreated());
        Assertions.assertThat(notificationConverted.getDateVisualized()).isEqualTo(notification.getDateVisualized());
    }
}
