package br.com.systec.fintrack.tenant.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.tenant.fake.TenantFake;
import br.com.systec.fintrack.tenant.impl.TenantServiceImpl;
import br.com.systec.fintrack.tenant.model.Tenant;
import br.com.systec.fintrack.tenant.repository.TenantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {
    @Mock
    private TenantRepository repository;
    @InjectMocks
    private TenantServiceImpl service;

    @Test
    void whenSaveTenantAndReturnTenantCreatedTest() throws BaseException {
        Tenant tenantReturn = TenantFake.fake();

        Mockito.when(repository.save(Mockito.any(Tenant.class))).thenReturn(tenantReturn);

        Tenant tenantSaveReturn = service.save(TenantFake.fake());

        Assertions.assertThat(tenantReturn.getId()).isEqualTo(tenantSaveReturn.getId());
        Assertions.assertThat(tenantReturn.getFederalId()).isEqualTo(tenantSaveReturn.getFederalId());
        Assertions.assertThat(tenantReturn.getName()).isEqualTo(tenantSaveReturn.getName());

        Mockito.verify(repository).save(Mockito.any(Tenant.class));
    }

    @Test
    void whenUpdateTenantAndReturnTenantCreatedTest() throws BaseException {
        Tenant tenantReturn = TenantFake.fake();

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(tenantReturn));
        Mockito.when(repository.update(Mockito.any(Tenant.class))).thenReturn(tenantReturn);

        Tenant tenantSaveReturn = service.update(1L, 1L);

        Assertions.assertThat(tenantReturn.getId()).isEqualTo(tenantSaveReturn.getId());
        Assertions.assertThat(tenantReturn.getFederalId()).isEqualTo(tenantSaveReturn.getFederalId());
        Assertions.assertThat(tenantReturn.getName()).isEqualTo(tenantSaveReturn.getName());
        Assertions.assertThat(tenantReturn.getUserPrincipalId()).isEqualTo(tenantSaveReturn.getUserPrincipalId());

        Mockito.verify(repository).update(Mockito.any(Tenant.class));
    }

    @Test
    void whenFindTenantByIdTest() throws BaseException{
        Tenant tenantReturn = TenantFake.fake();

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(tenantReturn));

        Tenant tenantReturnFind = service.findTenantById(1L);

        Assertions.assertThat(tenantReturn.getId()).isEqualTo(tenantReturnFind.getId());
        Assertions.assertThat(tenantReturn.getFederalId()).isEqualTo(tenantReturnFind.getFederalId());
        Assertions.assertThat(tenantReturn.getName()).isEqualTo(tenantReturnFind.getName());

        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void whenFindTenantByIdNotExistTest(){
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findTenantById(1L)).isInstanceOf(ObjectNotFoundException.class);
    }
}
