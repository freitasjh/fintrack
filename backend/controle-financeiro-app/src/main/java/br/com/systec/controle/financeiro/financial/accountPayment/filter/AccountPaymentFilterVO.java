package br.com.systec.controle.financeiro.financial.accountPayment.filter;

import br.com.systec.controle.financeiro.commons.filter.PageParamSearchVO;

public class AccountPaymentFilterVO  {
    private String keywordSearch;
    private Long bankAccountId;
    private AccountPaymentFilterType filterType;

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
}
