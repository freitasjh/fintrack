package br.com.systec.controle.financeiro.administrator.user.service;

import br.com.systec.controle.financeiro.administrator.tenant.model.Tenant;
import br.com.systec.controle.financeiro.administrator.tenant.service.TenantService;
import br.com.systec.controle.financeiro.administrator.user.exception.LoginEmailValidationException;
import br.com.systec.controle.financeiro.administrator.user.exception.LoginUsernameValidateException;
import br.com.systec.controle.financeiro.administrator.user.exception.UserNotFoundException;
import br.com.systec.controle.financeiro.administrator.user.model.Profile;
import br.com.systec.controle.financeiro.administrator.user.model.User;
import br.com.systec.controle.financeiro.administrator.user.repository.UserRepository;
import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.commons.exception.ObjectFoundException;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private TenantService tenantService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(final User user) {
        try {
            validateLoginAndEmailExist(user);

            User userToSave;

            if (user.getTenantId() == null && user.isUserPrincipalTenant()) {
                userToSave = saveTenantAndReturnUserAndTenantId(user);
            } else {
                userToSave = user;
            }

            User userSaved = repository.save(userToSave);
            user.getProfile().add(new Profile(1L));

            if (user.isUserPrincipalTenant()) {
                tenantService.update(userSaved.getTenantId(), userSaved.getTenantId());
            }

            rabbitTemplate.convertAndSend(RabbitMQConfig.FINANCIAL_EXCHANGE,RabbitMQConfig.ROUTING_KEY_NEW_ACCOUNT, userSaved.getTenantId());

            return userSaved;
        }catch (BaseException e){
            throw e;
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
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void validateLoginAndEmailExist(User user) {
        Optional<User> userReturn = repository.findByLoginOrEmail(user.getUsername(), user.getEmail());

        if(userReturn.isEmpty()){
            return;
        }

        if(userReturn.get().getUsername().equalsIgnoreCase(user.getUsername())) {
            throw new LoginUsernameValidateException();
        }else if(userReturn.get().getEmail().equalsIgnoreCase(user.getEmail())){
            throw new LoginEmailValidationException();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllByTenant() throws BaseException {
        try {
            return repository.findAllByTenant();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar os usuarios", e);
            throw new BaseException(e.getMessage(), e);
        }
    }
}
