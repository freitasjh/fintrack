package br.com.systec.fintrack.financial.transfer.v1.dto;

import java.util.Date;

public class AccountTransferResponseDTO {

    private Long id;
    private String description;
    private String bankToDescription;
    private String bankFromDescription;
    private double amount;
    private Date transferDate;

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

    public String getBankToDescription() {
        return bankToDescription;
    }

    public void setBankToDescription(String bankToDescription) {
        this.bankToDescription = bankToDescription;
    }

    public String getBankFromDescription() {
        return bankFromDescription;
    }

    public void setBankFromDescription(String bankFromDescription) {
        this.bankFromDescription = bankFromDescription;
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
}
