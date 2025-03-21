package br.com.systec.fintrack.user.impl.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import br.com.systec.fintrack.user.impl.repository.UserRepository;
import br.com.systec.fintrack.tenant.model.Tenant;
import br.com.systec.fintrack.tenant.service.TenantService;
import br.com.systec.fintrack.user.exception.LoginEmailValidationException;
import br.com.systec.fintrack.user.exception.LoginUsernameValidateException;
import br.com.systec.fintrack.user.exception.UserNotFoundException;
import br.com.systec.fintrack.user.fake.TenantFake;
import br.com.systec.fintrack.user.fake.UserFake;
import br.com.systec.fintrack.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@SpringBootConfiguration
public class UserServiceTest {

    @Mock
    private TenantService tenantService;
    @Mock
    private UserRepository repository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource

    @InjectMocks
    private I18nTranslate i18nTranslate;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void whenSaveNewUserAndTenantTest() throws BaseException {
        User userReturn = UserFake.fakeUser();
        Tenant tenantReturn = TenantFake.fake();
        User userToSave = UserFake.fakeUser();
        userToSave.setId(null);
        userToSave.setUserPrincipalTenant(true);
        userToSave.setTenantId(null);

        Mockito.when(tenantService.save(Mockito.any(Tenant.class))).thenReturn(tenantReturn);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(userReturn);

        User userSaved = userService.save(userToSave);

        Assertions.assertThat(userSaved.getId()).isEqualTo(userReturn.getId());
        Assertions.assertThat(userSaved.getTenantId()).isEqualTo(userReturn.getTenantId());
        Assertions.assertThat(userSaved.getFederalId()).isEqualTo(userReturn.getFederalId());

        Mockito.verify(tenantService).save(Mockito.any(Tenant.class));
        Mockito.verify(repository).save(Mockito.any(User.class));
    }

    @Test
    void whenSaveNewUserInTenantExistTest() throws BaseException{
        User userReturn = UserFake.fakeUser();
        User userToSave = UserFake.fakeUser();
        userToSave.setId(1L);
        userToSave.setUserPrincipalTenant(false);
        userToSave.setTenantId(1L);

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(userReturn);

        User userSaved = userService.save(userToSave);

        Assertions.assertThat(userSaved.getId()).isEqualTo(userReturn.getId());
        Assertions.assertThat(userSaved.getTenantId()).isEqualTo(userReturn.getTenantId());
        Assertions.assertThat(userSaved.getFederalId()).isEqualTo(userReturn.getFederalId());

        Mockito.verify(repository).save(Mockito.any(User.class));
        Mockito.verify(tenantService, Mockito.times(0)).save(Mockito.any(Tenant.class));
    }

    @Test
    void whenFindUserByIdTest() throws BaseException{
        User userReturn = UserFake.fakeUser();

        Mockito.doReturn(Optional.of(userReturn)).when(repository).findById(Mockito.anyLong());

        User userReturnFind = userService.findById(1L);

        Assertions.assertThat(userReturnFind.getId()).isEqualTo(userReturn.getId());
        Assertions.assertThat(userReturnFind.getTenantId()).isEqualTo(userReturn.getTenantId());
        Assertions.assertThat(userReturnFind.getFederalId()).isEqualTo(userReturn.getFederalId());

        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void whenFindUserByIdAndNotFoundExceptionTest() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.findById(1L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void whenUsernameIfExistException() {
        User userReturnFind = UserFake.fakeUser();
        userReturnFind.setEmail("teste@teste.com.br");

        Mockito.when(repository.findByLoginOrEmail(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(Optional.of(userReturnFind));

        Assertions.assertThatThrownBy(() -> userService.save(UserFake.fakeUser()))
                .isInstanceOf(LoginUsernameValidateException.class);
    }

    @Test
    void whenEmailIfExistException() {
        User userReturnFind = UserFake.fakeUser();
        userReturnFind.setUsername("teste");

        Mockito.when(repository.findByLoginOrEmail(Mockito.anyString(),
                Mockito.anyString())).thenReturn(Optional.of(userReturnFind));

        Assertions.assertThatThrownBy(() -> userService.save(UserFake.fakeUser()))
                .isInstanceOf(LoginEmailValidationException.class);
    }

    @Test
    void whenFindAllByTenant() {
        List<User> listReturnUser = List.of(UserFake.fakeUser());

        Mockito.doReturn(listReturnUser).when(repository).findAllByTenant();

        List<User> listReturned = userService.findAllByTenant();

        Assertions.assertThat(listReturned).isNotEmpty();
        Assertions.assertThat(listReturned.size()).isEqualTo(1);
        Assertions.assertThat(listReturned.get(0).getId()).isEqualTo(listReturnUser.get(0).getId());

        Mockito.verify(repository).findAllByTenant();
    }

    @Test
    void whenFindAllByTenantException() {
        Mockito.when(repository.findAllByTenant()).thenThrow(new RuntimeException());

        Assertions.assertThatThrownBy(() -> userService.findAllByTenant()).isInstanceOf(BaseException.class);
    }

    @Test
    void whenFindByLogin() {
        User userToReturn = UserFake.fakeUser();

        Mockito.doReturn(Optional.of(userToReturn)).when(repository).findByLogin(Mockito.anyString());

        User userReturned = userService.findByLogin("teste");

        Assertions.assertThat(userReturned).isNotNull();
        Assertions.assertThat(userReturned.getId()).isNotNull();
        Assertions.assertThat(userReturned.getId()).isEqualTo(userToReturn.getId());

        Mockito.verify(repository).findByLogin(Mockito.anyString());
    }

    @Test
    void whenFindByIdOrGetPrincipalWithNotUserId() {
        User userToReturn = UserFake.fakeUser();

        Mockito.doReturn(Optional.of(userToReturn)).when(repository).findUserPrincipal();

        User userReturned = userService.findByIdOrGetPrincipal(null);

        Assertions.assertThat(userReturned).isNotNull();
        Assertions.assertThat(userReturned.getId()).isNotNull();
        Assertions.assertThat(userReturned.getId()).isEqualTo(userToReturn.getId());

        Mockito.verify(repository).findUserPrincipal();
        Mockito.verify(repository, Mockito.times(0)).findById(Mockito.anyLong());
    }

    @Test
    void whenFindByIdOrGetPrincipalWithId() {
        User userToReturn = UserFake.fakeUser();

        Mockito.doReturn(Optional.of(userToReturn)).when(repository).findById(Mockito.anyLong());

        User userReturned = userService.findByIdOrGetPrincipal(1L);

        Assertions.assertThat(userReturned).isNotNull();
        Assertions.assertThat(userReturned.getId()).isNotNull();
        Assertions.assertThat(userReturned.getId()).isEqualTo(userToReturn.getId());

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(repository, Mockito.times(0)).findUserPrincipal();
    }

    @Test
    void whenUpdateUserTest() throws BaseException {
        User userToUpdate = UserFake.fakeUser();
        User updatedUser = UserFake.fakeUser();
        updatedUser.setName("Updated Name");

        Mockito.when(repository.update(Mockito.any(User.class))).thenReturn(updatedUser);

        User result = userService.update(userToUpdate);

        Assertions.assertThat(result.getName()).isEqualTo("Updated Name");

        Mockito.verify(repository).update(Mockito.any(User.class));
    }
}
