package br.com.systec.controle.financeiro.AccountReceivable.model;

import br.com.systec.controle.financeiro.commons.TenantContext;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "receive")
public class Receive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id")
    private Long tenantId;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "description")
    private String description;
    @Column(name = "date_register")
    private LocalDateTime dateRegister;
    @Column(name = "date_receiver")
    private LocalDateTime dateReceiver;

    public Receive(){
        this.tenantId = TenantContext.getTenant();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        if(tenantId == null){
            tenantId = TenantContext.getTenant();
        }
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }


    public LocalDateTime getDateReceiver() {
        return dateReceiver;
    }

    public void setDateReceiver(LocalDateTime dateReceiver) {
        this.dateReceiver = dateReceiver;
    }
}
