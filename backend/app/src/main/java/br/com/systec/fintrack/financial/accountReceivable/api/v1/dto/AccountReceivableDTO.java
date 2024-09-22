package br.com.systec.fintrack.financial.accountReceivable.api.v1.dto;

import java.util.Date;

public class AccountReceivableDTO {
    private Long id;
    private String description;
    private String bankAccountDescription;
    private double amount;
    private boolean processed;
    private Date dateRegister;
    private Date dateProcessed;


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

    public String getBankAccountDescription() {
        return bankAccountDescription;
    }

    public void setBankAccountDescription(String bankAccountDescription) {
        this.bankAccountDescription = bankAccountDescription;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
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
}
