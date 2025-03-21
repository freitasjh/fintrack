package br.com.systec.fintrack.notification.api.v1.dto;


import java.util.Date;

public class NotificationResponseDTO {

    private Long id;
    private String message;
    private boolean visualized;
    private Date dateCreated;
    private Date dateVisualized;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVisualized() {
        return visualized;
    }

    public void setVisualized(boolean visualized) {
        this.visualized = visualized;
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
