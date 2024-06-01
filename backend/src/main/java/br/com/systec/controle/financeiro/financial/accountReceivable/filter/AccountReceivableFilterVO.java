package br.com.systec.controle.financeiro.financial.accountReceivable.filter;

import br.com.systec.controle.financeiro.commons.filter.FilterSearchVO;
import br.com.systec.controle.financeiro.financial.accountReceivable.model.AccountReceivable;
import org.springframework.data.jpa.domain.Specification;

public class AccountReceivableFilterVO extends FilterSearchVO<AccountReceivable> {
    private Long accountId;
    private Long dateRegister;
    private Long dateReceiver;

    public AccountReceivableFilterVO() {
    }

    public AccountReceivableFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<AccountReceivable> getSpecification() {
        return AccountReceivableSpecification.findByKeywordAndTenant(accountId);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(Long dateRegister) {
        this.dateRegister = dateRegister;
    }

    public Long getDateReceiver() {
        return dateReceiver;
    }

    public void setDateReceiver(Long dateReceiver) {
        this.dateReceiver = dateReceiver;
    }
}
