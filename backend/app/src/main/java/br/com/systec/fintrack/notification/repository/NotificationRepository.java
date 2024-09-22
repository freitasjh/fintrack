package br.com.systec.fintrack.notification.repository;

import br.com.systec.fintrack.commons.AbstractRepository;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.notification.model.Notification;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NotificationRepository extends AbstractRepository<Notification, Long> {

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Notification> findByTenantIdAndUserIdAndNotVisualized(Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select obj from Notification obj where obj.tenantId = :tenantId ");
        sql.append(" and obj.userId = :userId and obj.visualized = :isVisualized ");
        sql.append(" order by obj.dateCreated desc ");

        TypedQuery<Notification> query = entityManager.createQuery(sql.toString(), Notification.class);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("userId", userId);
        query.setParameter("isVisualized", false);

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Long findTotalNotificationNotVisualizedByUserId(Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" Select count(obj.id) from Notification obj ");
        sql.append(" where obj.userId = :userId and obj.tenantId = :tenantId and obj.visualized = :isVisualized ");

        TypedQuery<Long> query = entityManager.createQuery(sql.toString(), Long.class);
        query.setParameter("userId", userId);
        query.setParameter("tenantId", TenantContext.getTenant());
        query.setParameter("isVisualized", false);

        return query.getSingleResult();
    }
}
