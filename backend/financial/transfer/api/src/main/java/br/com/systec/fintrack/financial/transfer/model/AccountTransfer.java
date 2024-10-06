package br.com.systec.fintrack.financial.transfer.model;

import br.com.systec.fintrack.bankAccount.model.BankAccount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "account_transfer")
public class AccountTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bank_account_from_id")
    private BankAccount bankAccountFrom;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bank_account_to_id")
    private BankAccount bankAccountTo;
    @Column(name = "amount")
    private double amount;
    @Column(name = "transfer_date")
    private Date transferDate;
    @Column(name = "tenant_id")
    private Long tenantId;

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

    public BankAccount getBankAccountFrom() {
        return bankAccountFrom;
    }

    public void setBankAccountFrom(BankAccount bankAccountFrom) {
        this.bankAccountFrom = bankAccountFrom;
    }

    public BankAccount getBankAccountTo() {
        return bankAccountTo;
    }

    public void setBankAccountTo(BankAccount bankAccountTo) {
        this.bankAccountTo = bankAccountTo;
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
        if(transferDate == null) {
            transferDate = new Date();
        }
        this.transferDate = transferDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
