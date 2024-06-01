package br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public class AccountReceivableInputDTO {

    private Long id;
    @NotNull
    private Long accountId;
    @NotNull
    private double amount;
    @NotNull
    private String description;
    @NotNull
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
        return "AccountReceivableInputDTO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateRegister=" + dateRegister +
                ", dateReceiver=" + dateReceiver +
                '}';
    }
}
