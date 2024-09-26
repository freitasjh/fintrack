package br.com.systec.fintrack.notification.service;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.notification.repository.NotificationRepository;
import br.com.systec.fintrack.user.model.User;
import br.com.systec.fintrack.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public Notification save(Notification notification) throws BaseException {
        try{
            if (notification.getTenantId() == null) {
                notification.setTenantId(TenantContext.getTenant());
            }

            User user = userService.findById(notification.getUserId());
            notification.setUserEmail(user.getEmail());

            Notification notificationSaved = repository.save(notification);
            sendWebSocketNotificationToUserEmail(user, null);

            return notificationSaved;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar a notificacao", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar a notificacao", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) throws BaseException {
        try {
            repository.deleteById(id);
        }catch (Exception e) {
            log.error("Ocorreu um erro ao tentar deletar a notificacao", e);
            throw new BaseException("Ocorreu um erro ao tentar deletar a notificação", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Notification> findByTenantAndUserIdAndNotVisualized(Long userId) throws BaseException {
        try {
             List<Notification> listOfNotification = repository.findByTenantIdAndUserIdAndNotVisualized(userId);
             return listOfNotification;
        } catch (Exception e) {
           log.error("Erro ao tentar pesquisar as notificacoes", e);
           throw new BaseException("Erro ao tentar pesquisar as notificacoes", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Long findTotalNotificationByUserId(Long userId) throws BaseException {
        try {
            return repository.findTotalNotificationNotVisualizedByUserId(userId);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pegar o total de notificacao", e);
            throw new BaseException("Ocorreu um erro ao tentar pegar o total de notificacao", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendWebSocketNotificationToUserEmail(User user, Long userId) throws BaseException {
        if(user == null){
            user = userService.findById(userId);
        }
        messageTemplate.convertAndSend("/user/"+user.getEmail()+"/queue/notification", "notificacao");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void sendEmailNotification() throws BaseException {
        //TODO vai ser feita a logica para enviar e-mail
    }
}
