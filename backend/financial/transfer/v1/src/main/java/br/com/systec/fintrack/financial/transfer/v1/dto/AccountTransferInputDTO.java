package br.com.systec.fintrack.financial.transfer.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class AccountTransferInputDTO {
    private Long id;
    private String description;
    @NotNull
    private Long bankAccountFromId;
    @NotNull
    private Long bankAccountToId;
    @NotNull
    private double amount;
    private Date transferDate;
    private Long tenantId;

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

    public Long getBankAccountFromId() {
        return bankAccountFromId;
    }

    public void setBankAccountFromId(Long bankAccountFromId) {
        this.bankAccountFromId = bankAccountFromId;
    }

    public Long getBankAccountToId() {
        return bankAccountToId;
    }

    public void setBankAccountToId(Long bankAccountToId) {
        this.bankAccountToId = bankAccountToId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
