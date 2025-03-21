package br.com.systec.fintrack.user.impl.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import br.com.systec.fintrack.user.impl.repository.UserRepository;
import br.com.systec.fintrack.tenant.model.Tenant;
import br.com.systec.fintrack.tenant.service.TenantService;
import br.com.systec.fintrack.user.exception.LoginEmailValidationException;
import br.com.systec.fintrack.user.exception.LoginUsernameValidateException;
import br.com.systec.fintrack.user.exception.UserNotFoundException;
import br.com.systec.fintrack.user.model.Profile;
import br.com.systec.fintrack.user.model.User;
import br.com.systec.fintrack.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private TenantService tenantService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(final User user) throws BaseException {
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

            rabbitTemplate.convertAndSend(RabbitMQConstants.FINANCIAL_EXCHANGE, RabbitMQConstants.ROUTING_KEY_NEW_ACCOUNT, userSaved.getTenantId());

            return userSaved;
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao tentar salvar o usuario", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar o usuario", e);
        }
    }

    private User saveTenantAndReturnUserAndTenantId(User user) {
        try {
            Tenant tenant = new Tenant(user.getName(), user.getFederalId());
            Tenant tenantSaved = tenantService.save(tenant);
            user.setTenantId(tenantSaved.getId());

            return user;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar criar a nova conta", e);
            throw new BaseException("Ocorreu um erro ao tentar criar a nova conta", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(User user) throws BaseException {
        try {
            return repository.update(user);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualziar o usuario", e);
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findById(Long userId) throws BaseException {
        try {
            return repository.findById(userId).orElseThrow(UserNotFoundException::new);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar o usuario pelo id", e);
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findByIdOrGetPrincipal(Long userId) throws BaseException {
        try {
            if (userId == null) {
                return repository.findUserPrincipal().orElseThrow(UserNotFoundException::new);
            }

            return findById(userId);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar o usuario", e);
            throw new BaseException(e);
        }
    }

    private void validateLoginAndEmailExist(User user) {
        Optional<User> userReturn = repository.findByLoginOrEmail(user.getUsername(), user.getEmail());

        if (userReturn.isEmpty()) {
            return;
        }

        if (userReturn.get().getUsername().equalsIgnoreCase(user.getUsername())) {
            throw new LoginUsernameValidateException();
        } else if (userReturn.get().getEmail().equalsIgnoreCase(user.getEmail())) {
            throw new LoginEmailValidationException();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllByTenant() throws BaseException {
        try {
            return repository.findAllByTenant();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar os usuarios", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findByLogin(String login) throws BaseException {
        try {
            return repository.findByLogin(login).orElseThrow(UserNotFoundException::new);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar o usuario pelo login {}", login, e);
            throw new BaseException(e);
        }
    }
}
