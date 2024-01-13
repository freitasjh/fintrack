package br.com.systec.controle.financeiro.administrator.user.service;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;
import br.com.systec.controle.financeiro.administrator.tenant.service.TenantService;
import br.com.systec.controle.financeiro.administrator.user.model.User;
import br.com.systec.controle.financeiro.administrator.user.repository.UserRepository;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private TenantService tenantService;

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(final User user) {
        try {
            User userToSave;

            if (user.getTenantId() == null && user.isUserPrincipalTenant()) {
                userToSave = saveTenantAndReturnUserAndTenantId(user);
            } else {
                userToSave = user;
            }

            User userSaved = repository.save(userToSave);

            if (user.isUserPrincipalTenant()) {
                tenantService.update(userSaved.getTenantId(), userSaved.getTenantId());
            }

            return userSaved;
        }catch (Exception e){
            log.error("Erro ao tentar salvar o usuario", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar o usuario", e);
        }
    }

    private User saveTenantAndReturnUserAndTenantId(User user) {
        try{
            Tenant tenant = new Tenant(user);
            Tenant tenantSaved = tenantService.save(tenant);
            user.setTenantId(tenantSaved.getId());

            return user;
        }catch (Exception e) {
            log.error("Ocorreu um erro ao tentar criar a nova conta", e);
            throw new BaseException("Ocorreu um erro ao tentar criar a nova conta", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user){
         User userSaved = repository.update(user);

        return userSaved;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findById(Long userId){
        User user = repository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        return user;
    }
}
