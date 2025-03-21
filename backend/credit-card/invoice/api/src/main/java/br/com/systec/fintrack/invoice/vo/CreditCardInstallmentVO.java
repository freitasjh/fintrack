package br.com.systec.fintrack.invoice.vo;

import java.time.LocalDate;

public class CreditCardInstallmentVO {

    private String description;
    private double amount;
    private LocalDate dateCreate;
    private int installment;

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

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }


    public static class Builder {
        private String description;
        private double amount;
        private LocalDate dateCreate;
        private int installment;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder dateCreate(LocalDate dateCreate) {
            this.dateCreate = dateCreate;
            return this;
        }

        public Builder installment(int installment) {
            this.installment = installment;
            return this;
        }

        public CreditCardInstallmentVO build() {
            CreditCardInstallmentVO installmentVO = new CreditCardInstallmentVO();
            installmentVO.setDescription(this.description);
            installmentVO.setAmount(this.amount);
            installmentVO.setDateCreate(this.dateCreate);
            installmentVO.setInstallment(this.installment);
            return installmentVO;
        }
    }
}
