package br.com.systec.fintrack.financial.payment.filter;

import java.time.LocalDate;

public class AccountPaymentFilterVO {
    private String keywordSearch;
    private Long bankAccountId;
    private AccountPaymentFilterType filterType;
    private LocalDate dateProcessedInitial;
    private LocalDate dateProcessedFinal;
    private LocalDate datePaymentInitial;
    private LocalDate datePaymentFinal;


    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void setFilterType(AccountPaymentFilterType filterType) {
        this.filterType = filterType;
    }

    public AccountPaymentFilterType getFilterType() {
        return filterType;
    }

    public String getKeywordSearch() {
        return keywordSearch;
    }

    public void setKeywordSearch(String keywordSearch) {
        this.keywordSearch = keywordSearch;
    }

    public LocalDate getDateProcessedInitial() {
        return dateProcessedInitial;
    }

    public void setDateProcessedInitial(LocalDate dateProcessedInitial) {
        this.dateProcessedInitial = dateProcessedInitial;
    }

    public LocalDate getDateProcessedFinal() {
        return dateProcessedFinal;
    }

    public void setDateProcessedFinal(LocalDate dateProcessedFinal) {
        this.dateProcessedFinal = dateProcessedFinal;
    }

    public LocalDate getDatePaymentInitial() {
        return datePaymentInitial;
    }

    public void setDatePaymentInitial(LocalDate datePaymentInitial) {
        this.datePaymentInitial = datePaymentInitial;
    }

    public LocalDate getDatePaymentFinal() {
        return datePaymentFinal;
    }

    public void setDatePaymentFinal(LocalDate datePaymentFinal) {
        this.datePaymentFinal = datePaymentFinal;
    }
}
