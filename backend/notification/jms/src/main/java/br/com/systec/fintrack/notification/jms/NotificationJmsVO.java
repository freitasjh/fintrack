package br.com.systec.fintrack.notification.jms;

public class NotificationJmsVO {

    private Long tenantId;
    private Long userId;
    private NotificationType notificationType;
    private String message;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationJmsVO{" +
                "tenantId=" + tenantId +
                ", userId=" + userId +
                ", notificationType=" + notificationType +
                ", message='" + message + '\'' +
                '}';
    }

    public enum NotificationType {
        NOTIFICATION_RECEIVE(1),
        NOTIFICATION_PAYMENT(2),
        NOTIFICATION_TRANSFER(3),
        NOTIFICATION_PENDING(4);

        int code;

        NotificationType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
