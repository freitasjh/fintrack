package br.com.systec.fintrack.notification.v1.converter;

import br.com.systec.fintrack.notification.api.v1.converter.NotificationMapper;
import br.com.systec.fintrack.notification.api.v1.dto.NotificationResponseDTO;
import br.com.systec.fintrack.notification.api.v1.dto.NotificationInputDTO;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.notification.v1.fake.NotificationFake;
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

        NotificationResponseDTO notificationConverted = NotificationMapper.toResponseDTO(notification);

        Assertions.assertThat(notificationConverted).isNotNull();
        Assertions.assertThat(notificationConverted.getId()).isEqualTo(notification.getId());
        Assertions.assertThat(notificationConverted.getMessage()).isEqualTo(notification.getMessage());
        Assertions.assertThat(notificationConverted.getDateCreated()).isEqualTo(notification.getDateCreated());
        Assertions.assertThat(notificationConverted.getDateVisualized()).isEqualTo(notification.getDateVisualized());
    }
}
