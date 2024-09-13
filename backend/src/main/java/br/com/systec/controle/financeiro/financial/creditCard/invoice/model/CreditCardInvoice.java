package br.com.systec.controle.financeiro.financial.creditCard.invoice.model;

import br.com.systec.controle.financeiro.commons.model.BaseModel;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;


@Entity
@Table(name = "credit_card_invoice")
public class CreditCardInvoice extends BaseModel {

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = CreditCard.class)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private InvoiceStatusType statusType;
    @Column(name = "date_close")
    private LocalDate dateClose;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "tenant_id")
    private Long tenantId;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public InvoiceStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(InvoiceStatusType statusType) {
        this.statusType = statusType;
    }

    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
