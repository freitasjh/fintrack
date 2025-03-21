package br.com.systec.fintrack.notification.impl.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.notification.impl.fake.NotificationFake;
import br.com.systec.fintrack.notification.impl.fake.UserFake;
import br.com.systec.fintrack.notification.impl.repository.NotificationRepository;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.user.model.User;
import br.com.systec.fintrack.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@SpringBootConfiguration
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private SimpMessagingTemplate messageTemplate;;

    @InjectMocks
    private NotificationServiceImpl service = Mockito.spy(new NotificationServiceImpl());

    @Test
    void whenSaveNotification() {
        TenantContext.add(1L);
        Notification notificationToSave = NotificationFake.toFake();
        notificationToSave.setId(null);
        notificationToSave.setTenantId(null);

        User user = UserFake.fake();
        Notification notificationReturn = NotificationFake.toFake();


        Mockito.doReturn(notificationReturn).when(repository).save(Mockito.any(Notification.class));
        Mockito.doReturn(user).when(userService).findByIdOrGetPrincipal(Mockito.anyLong());

        Notification notificationSaved = service.save(notificationToSave);

        Assertions.assertThat(notificationSaved).isNotNull();
        Assertions.assertThat(notificationSaved.getId()).isEqualTo(notificationReturn.getId());

        Mockito.verify(repository).save(Mockito.any(Notification.class));
        Mockito.verify(userService).findByIdOrGetPrincipal(Mockito.anyLong());
    }

    @Test
    void whenSaveNotificationException() {
        TenantContext.add(1L);

        User user = UserFake.fake();
        Notification notificationToSave = NotificationFake.toFake();
        notificationToSave.setId(null);
        notificationToSave.setTenantId(null);

        Mockito.doThrow(new RuntimeException()).when(repository).save(Mockito.any(Notification.class));
        Mockito.doReturn(user).when(userService).findByIdOrGetPrincipal(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.save(notificationToSave))
                .isInstanceOf(BaseException.class);

        Mockito.verify(userService).findByIdOrGetPrincipal(Mockito.anyLong());
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

    @Test
    void whenFindByTenantAndUserIdAndNotVisualizedException() {
        TenantContext.add(1L);

        Mockito.doThrow(new RuntimeException()).when(repository).findByTenantIdAndUserIdAndNotVisualized(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.findByTenantAndUserIdAndNotVisualized(1L))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).findByTenantIdAndUserIdAndNotVisualized(Mockito.anyLong());
    }

    @Test
    void whenFindTotalNotificationByUserId() {
        TenantContext.add(1L);

        Mockito.doReturn(1L).when(repository).findTotalNotificationNotVisualizedByUserId(Mockito.anyLong());

        Long totalNotification = service.findTotalNotificationByUserId(1L);

        Assertions.assertThat(totalNotification).isNotNull();
        Assertions.assertThat(totalNotification).isEqualTo(1L);

        Mockito.verify(repository).findTotalNotificationNotVisualizedByUserId(Mockito.anyLong());
    }

    @Test
    void whenFindTotalNotificationByUserIdException() {
        TenantContext.add(1L);

        Mockito.doThrow(new RuntimeException()).when(repository).findTotalNotificationNotVisualizedByUserId(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.findTotalNotificationByUserId(1L))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).findTotalNotificationNotVisualizedByUserId(Mockito.anyLong());
    }

    @Test
    void whenDeleteNotification() {
        TenantContext.add(1L);

        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());

        service.delete(1L);

        Mockito.verify(repository).deleteById(Mockito.anyLong());
    }

    @Test
    void whenDeleteNotificationException() {
        TenantContext.add(1L);

        Mockito.doThrow(new RuntimeException()).when(repository).deleteById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).deleteById(Mockito.anyLong());
    }
}
