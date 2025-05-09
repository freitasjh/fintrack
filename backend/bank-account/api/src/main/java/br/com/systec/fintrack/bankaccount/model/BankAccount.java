package br.com.systec.fintrack.bankaccount.model;

import br.com.systec.fintrack.bank.model.Bank;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "agency")
    private String agency;
    @Column(name = "account")
    private String account;
    @Column(name = "account_type")
    @Enumerated(EnumType.ORDINAL)
    private AccountType accountType;
    @Column(name = "balance")
    private double balance;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @Column(name = "tenant_id")
    private Long tenantId;
    @Transient
    private Double initialValue;

    public BankAccount() {
    }

    public BankAccount(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank == null ? null : new Bank(bank.getId());
    }

    public void setBank(Bank bank) {
        this.bank = null == bank ? null : new Bank(bank.getId());
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Double initialValue) {
        this.initialValue = initialValue;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", agency='" + agency + '\'' +
                ", account='" + account + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", bank=" + bank +
                ", tenantId=" + tenantId +
                '}';
    }
}
