package br.com.systec.fintrack.creditcard.model;

import br.com.systec.fintrack.bankaccount.model.BankAccount;
import br.com.systec.fintrack.commons.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;


@Table(name = "credit_card")
@Entity
public class CreditCard extends BaseModel {

    @Serial
    private static final long serialVersionUID = 678434691774358394L;
    @Column(name = "name")
    private String name;
    @Column(name = "number")
    private String number;
    @Column(name = "cvc")
    private String cvc;
    @Column(name = "name_credit_card")
    private String nameCreditCard;
    @Column(name = "total_limit")
    private double totalLimit;
    @Column(name = "available_limit")
    private double availableLimit;
    @Column(name = "due_date")
    private String dueDay;
    @Column(name = "closing_date")
    private String closingDate;
    @Column(name = "brand")
    @Enumerated(EnumType.ORDINAL)
    private BrandType brand;
    @Column(name = "interest_rate")
    private double interestRate;
    @Column(name = "status")
    private CreditCardStatusType status;
    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @PrePersist
    void preSave() {
        this.dateCreated = LocalDate.now();
        this.dateUpdated = LocalDate.now();
        if(status == null) {
            this.status = CreditCardStatusType.ACTIVE;
        }
        if(availableLimit == 0.0) {
            availableLimit = totalLimit;
        }
    }

    @PreUpdate
    void preUpdate() {
        this.dateUpdated = LocalDate.now();
    }


    public CreditCard() {
    }
    public CreditCard(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNameCreditCard() {
        return nameCreditCard;
    }

    public void setNameCreditCard(String nameCreditCard) {
        this.nameCreditCard = nameCreditCard;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(double availableLimit) {
        this.availableLimit = availableLimit;
    }

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public BrandType getBrand() {
        return brand;
    }

    public void setBrand(BrandType brand) {
        this.brand = brand;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public CreditCardStatusType getStatus() {
        return status;
    }

    public void setStatus(CreditCardStatusType status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

}
