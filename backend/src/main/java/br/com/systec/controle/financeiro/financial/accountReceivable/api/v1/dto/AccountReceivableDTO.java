package br.com.systec.controle.financeiro.financial.accountReceivable.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AccountReceivableDTO {

    private Long id;
    private String description;
    private double amount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
        return "AccountReceivableDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", dateRegister=" + dateRegister +
                ", dateReceiver=" + dateReceiver +
                '}';
    }
}
