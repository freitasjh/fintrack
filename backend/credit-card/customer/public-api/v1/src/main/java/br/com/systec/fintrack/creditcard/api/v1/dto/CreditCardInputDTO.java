package br.com.systec.fintrack.creditcard.api.v1.dto;

import br.com.systec.fintrack.creditcard.model.BrandType;
import br.com.systec.fintrack.creditcard.model.CreditCardStatusType;
import jakarta.validation.constraints.NotNull;

public class CreditCardInputDTO {
    private Long id;
    @NotNull(message = "Informe um nome para o cartão")
    private String name;
    @NotNull(message = "Informe o numero do cartão")
    private String number;
    private String cvc;
    @NotNull(message = "Informe o nome que esta no cartão")
    private String nameCreditCard;
    @NotNull(message = "Informe o limite total do cartão")
    private double totalLimit;
    private double availableLimit;
    @NotNull
    private String dueDay;
    @NotNull
    private String closingDate;
    private BrandType brand;
    private double interestRate;
    private CreditCardStatusType status;
    @NotNull(message = "Infome a conta do Cartão")
    private Long bankAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getDueDay() {
        return dueDay;
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

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
