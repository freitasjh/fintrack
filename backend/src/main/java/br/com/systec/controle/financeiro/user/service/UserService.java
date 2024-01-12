package br.com.systec.controle.financeiro.user.service;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;
import br.com.systec.controle.financeiro.administrator.tenant.service.TenantService;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.user.model.User;
import br.com.systec.controle.financeiro.user.repository.UserRepository;
import br.com.systec.controle.financeiro.user.v1.converter.UserConverter;
import br.com.systec.controle.financeiro.user.v1.dto.UserInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TenantService tenantService;

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(final User user) throws BaseException {
        User userToSave;

        if(user.getTenantId() == null && user.isUserPrincipalTenant()){
            userToSave = saveTenantAndReturnUserAndTenantId(user);
        }else {
            userToSave = user;
        }

        User userSaved = repository.save(userToSave);

        if(user.isUserPrincipalTenant()){
            tenantService.update(userSaved.getTenantId(), userSaved.getTenantId());
        }

        return userSaved;
    }

    private User saveTenantAndReturnUserAndTenantId(User user) throws BaseException{
        Tenant tenant = new Tenant(user);
        Tenant tenantSaved = tenantService.save(tenant);
        user.setTenantId(tenantSaved.getId());

        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user){
         User userSaved = repository.update(user);

        return userSaved;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findById(Long userId) throws ObjectNotFoundException{
        User user = repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        return user;

    }
}
