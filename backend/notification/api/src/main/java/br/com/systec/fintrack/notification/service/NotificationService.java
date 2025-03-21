package br.com.systec.fintrack.notification.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.notification.model.Notification;
import br.com.systec.fintrack.user.model.User;

import java.util.List;

public interface NotificationService {
    Notification save(Notification notification) throws BaseException;

    void delete(Long id) throws BaseException;

    List<Notification> findByTenantAndUserIdAndNotVisualized(Long userId) throws BaseException;

    Long findTotalNotificationByUserId(Long userId) throws BaseException;
}
