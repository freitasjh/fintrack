package br.com.systec.controle.financeiro.dashboard.api.v1.dto;

import java.util.Date;

public class AccountPaymentDashboardDTO {

    private Long id;
    private String description;
    private Date dateRegister;
    private Date dateProcessed;
    private double amount;
    private String category;
    private Date paymentDueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Date paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    @Override
    public String toString() {
        return "AccountPaymentDashboardDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateRegister=" + dateRegister +
                ", dateProcessed=" + dateProcessed +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", paymentDueDate=" + paymentDueDate +
                '}';
    }
}
