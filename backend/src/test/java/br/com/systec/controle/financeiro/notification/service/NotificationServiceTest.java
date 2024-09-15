package br.com.systec.controle.financeiro.notification.service;

import br.com.systec.controle.financeiro.administrator.user.model.User;
import br.com.systec.controle.financeiro.administrator.user.service.UserService;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.notification.fake.NotificationFake;
import br.com.systec.controle.financeiro.notification.model.Notification;
import br.com.systec.controle.financeiro.notification.repository.NotificationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class NotificationServiceTest {
    @Mock
    private NotificationRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private SimpMessagingTemplate messageTemplate;;
    @InjectMocks
    private NotificationService service;

    @Test
    void whenSaveNotification() {
        TenantContext.add(1L);
        Notification notificationToSave = NotificationFake.toFake();
        notificationToSave.setId(null);
        notificationToSave.setTenantId(null);

        Notification notificationReturn = NotificationFake.toFake();
        User user = new User();
        user.setEmail("freitasjh@gmail.com");

        Mockito.doReturn(notificationReturn).when(repository).save(Mockito.any(Notification.class));
        Mockito.doReturn(user).when(userService).findById(Mockito.anyLong());

        Notification notificationSaved = service.save(notificationToSave);

        Assertions.assertThat(notificationSaved).isNotNull();
        Assertions.assertThat(notificationSaved.getId()).isEqualTo(notificationReturn.getId());

        Mockito.verify(repository).save(Mockito.any(Notification.class));
        Mockito.verify(userService).findById(Mockito.anyLong());
    }

    @Test
    void whenFindByTenantAndUserIdAndNotVisualized() {
        TenantContext.add(1L);
        List<Notification> listOfNotificationReturn = Arrays.asList(NotificationFake.toFake());

        Mockito.doReturn(listOfNotificationReturn).when(repository).findByTenantIdAndUserIdAndNotVisualized(Mockito.anyLong());

        List<Notification> listOfNotification = service.findByTenantAndUserIdAndNotVisualized(1L);

        Assertions.assertThat(listOfNotification).isNotNull();
        Assertions.assertThat(listOfNotification).isNotEmpty();

        Mockito.verify(repository).findByTenantIdAndUserIdAndNotVisualized(Mockito.anyLong());
    }
}
