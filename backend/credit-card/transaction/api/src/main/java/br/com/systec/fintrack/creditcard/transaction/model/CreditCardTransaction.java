package br.com.systec.fintrack.creditcard.transaction.model;

import br.com.systec.fintrack.commons.model.BaseModel;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.repository.EntityGraph;

import java.io.Serial;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credit_card_transaction")
public class CreditCardTransaction extends BaseModel {

    @Serial
    private static final long serialVersionUID = -2332397896312546096L;
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
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<CreditCardInstallment> listOfInstallment;
    @Column(name = "date_transaction")
    private LocalDate dateTransaction;

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

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}
