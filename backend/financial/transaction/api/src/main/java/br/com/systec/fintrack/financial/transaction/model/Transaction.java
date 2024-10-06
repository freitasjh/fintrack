package br.com.systec.fintrack.financial.transaction.model;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.model.TransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;


@Table
@Entity(name = "transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;
    @Column(name = "amount")
    private double amount;
    @Column(name = "date_processed")
    private Date dateProcessed;
    @Column(name = "date_register")
    private Date dateRegister;
    @Column(name = "transaction_type", insertable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private TransactionType transactionType;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "description")
    private String description;
    @Column(name = "processed")
    private boolean processed;
    @Column(name = "tenant_id")
    private Long tenantId;
    @Column(name = "category_transaction_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CategoryTransactionType categoryTransactionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(Date dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public Date getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Date dateRegister) {
        this.dateRegister = dateRegister;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Long getTenantId() {
        if (tenantId == null) {
            tenantId = TenantContext.getTenant();
        }
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CategoryTransactionType getCategoryTransactionType() {
        return categoryTransactionType;
    }

    public void setCategoryTransactionType(CategoryTransactionType categoryTransactionType) {
        this.categoryTransactionType = categoryTransactionType;
    }
}
