package br.com.systec.controle.financeiro.administrator.user.service;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;
import br.com.systec.controle.financeiro.administrator.tenant.service.TenantService;
import br.com.systec.controle.financeiro.administrator.tenant.service.TenantServiceTest;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.fake.TenantFake;
import br.com.systec.controle.financeiro.fake.UserFake;
import br.com.systec.controle.financeiro.user.model.User;
import br.com.systec.controle.financeiro.user.repository.UserRepository;
import br.com.systec.controle.financeiro.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private TenantService tenantService;
    @Mock
    private UserRepository repository;
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
    void whenUpdateUserTest() {}

    @Test
    void whenFindUserByIdTest() {}

    @Test
    void whenFindUserByIdAndNotFoundExceptionTest() {}
}
