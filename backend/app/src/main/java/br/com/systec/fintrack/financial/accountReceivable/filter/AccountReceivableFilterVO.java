package br.com.systec.fintrack.financial.accountReceivable.filter;

import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import br.com.systec.fintrack.financial.accountReceivable.model.AccountReceivable;
import org.springframework.data.jpa.domain.Specification;

public class AccountReceivableFilterVO extends PageParamSearchVO<AccountReceivable> {
    private Long accountId;
    private Long dateRegister;
    private Long dateReceiver;

    public AccountReceivableFilterVO() {
        super(30,0, null);
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
