package br.com.systec.controle.financeiro.financial.creditCard.transaction.model;

import br.com.systec.controle.financeiro.commons.model.BaseModel;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credit_card_transaction")
public class CreditCardTransaction extends BaseModel {

    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private double amount;
    @Column(name = "installments")
    private int installments;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @Column(name = "tenant_id")
    private Long tenantId;
    @OneToMany(targetEntity = CreditCardInstallment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private List<CreditCardInstallment> listOfInstallment;

    @PrePersist
    void prePersist() {
        this.dateCreated = LocalDate.now();
        this.dateUpdated = LocalDate.now();
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

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<CreditCardInstallment> getListOfInstallment() {
        if (listOfInstallment == null) {
            listOfInstallment = new ArrayList<>();
        }
        return listOfInstallment;
    }

    public void setListOfInstallment(List<CreditCardInstallment> listOfInstallment) {
        this.listOfInstallment = listOfInstallment;
    }
}
