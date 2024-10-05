package br.com.systec.fintrack.notification.api.v1.dto;

public class NotificationInputDTO {

    private Long id;
    private Long userId;
    private Long tenantId;
    private String message;
    private int notificationTypeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public int getNotificationTypeCode() {
        return notificationTypeCode;
    }

    public void setNotificationTypeCode(int notificationTypeCode) {
        this.notificationTypeCode = notificationTypeCode;
    }
}
