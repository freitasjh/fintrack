package br.com.systec.fintrack.notification.model;

import br.com.systec.fintrack.notification.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "visualized")
    private boolean visualized;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;
    @Column(name = "message")
    private String message;
    @Column(name = "notification_type")
    @Enumerated(EnumType.ORDINAL)
    private NotificationType notificationType;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "date_visualized")
    private Date dateVisualized;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVisualized() {
        return visualized;
    }

    public void setVisualized(boolean visualized) {
        this.visualized = visualized;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateVisualized() {
        return dateVisualized;
    }

    public void setDateVisualized(Date dateVisualized) {
        this.dateVisualized = dateVisualized;
    }
}
