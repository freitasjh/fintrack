package br.com.systec.controle.financeiro.financial.creditCard.transaction.filter;

import java.time.LocalDate;

public class CreditCardTransactionFilterVO {
    private String keyword;
    private Long creditCardId;
    private LocalDate dateFilter;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public LocalDate getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(LocalDate dateFilter) {
        this.dateFilter = dateFilter;
    }
}
