package br.com.systec.fintrack.administrator.bankAccount.api.v1.dto;

public class BankAccountDTO {

    private Long id;
    private String description;
    private String agency;
    private String account;
    private String bankDescription;
    private double balance;

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

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
