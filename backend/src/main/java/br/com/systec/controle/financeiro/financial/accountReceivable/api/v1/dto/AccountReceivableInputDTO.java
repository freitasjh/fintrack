package br.com.systec.controle.financeiro.AccountReceivable.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


public class ReceiveInputDTO {

    private Long id;
    private Long tenantId;
    private Long accountId;
    private double amount;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateRegister;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateReceiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }

    public LocalDateTime getDateReceiver() {
        return dateReceiver;
    }

    public void setDateReceiver(LocalDateTime dateReceiver) {
        this.dateReceiver = dateReceiver;
    }

    @Override
    public String toString() {
        return "ReceiveInputDTO{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateRegister=" + dateRegister +
                ", dateReceiver=" + dateReceiver +
                '}';
    }
}
