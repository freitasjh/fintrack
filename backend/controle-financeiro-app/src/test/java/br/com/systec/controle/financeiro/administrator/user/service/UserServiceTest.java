package br.com.systec.controle.financeiro.administrator.user.service;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;
import br.com.systec.controle.financeiro.administrator.tenant.service.TenantService;
import br.com.systec.controle.financeiro.administrator.user.exception.LoginEmailValidationException;
import br.com.systec.controle.financeiro.administrator.user.exception.LoginUsernameValidateException;
import br.com.systec.controle.financeiro.administrator.user.exception.UserNotFoundException;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.fake.TenantFake;
import br.com.systec.controle.financeiro.fake.UserFake;
import br.com.systec.controle.financeiro.administrator.user.model.User;
import br.com.systec.controle.financeiro.administrator.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private TenantService tenantService;
    @Mock
    private UserRepository repository;
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private UserService userService;

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
}
