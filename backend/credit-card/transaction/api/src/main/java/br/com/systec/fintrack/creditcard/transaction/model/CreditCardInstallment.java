package br.com.systec.fintrack.creditcard.transaction.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "credit_card_installment")
public class CreditCardInstallment implements Serializable {

    @Serial
    private static final long serialVersionUID = -987178004044792203L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private double amount;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "date_create")
    private LocalDate dateCreate;
    @Column(name = "installment")
    private int installment;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private CreditCardTransaction transaction;
    @Column(name = "credit_card_invoice_id")
    private Long creditCardInvoiceId;
    @Column(name = "date_paid")
    private LocalDate datePaid;

    @PrePersist
    void prePersist(){
        this.dateCreate = LocalDate.now();
    }

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public CreditCardTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(CreditCardTransaction transaction) {
        this.transaction = transaction;
    }

    public Long getCreditCardInvoiceId() {
        return creditCardInvoiceId;
    }

    public void setCreditCardInvoiceId(Long creditCardInvoiceId) {
        this.creditCardInvoiceId = creditCardInvoiceId;
    }

    public LocalDate getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(LocalDate datePaid) {
        this.datePaid = datePaid;
    }

    @Override
    public String toString() {
        return "CreditCardInstallment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", dateCreate=" + dateCreate +
                ", installment=" + installment +
                ", transaction=" + transaction +
                ", creditCardInvoiceId=" + creditCardInvoiceId +
                ", dayPaid=" + datePaid +
                '}';
    }
}
