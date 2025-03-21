package br.com.systec.fintrack.financial.received.filter;

import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import br.com.systec.fintrack.financial.received.model.AccountReceivable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AccountReceivableFilterVO extends PageParamSearchVO<AccountReceivable> {
    private Long accountId;
    private LocalDate dateProcessed;

    public AccountReceivableFilterVO() {
        super(30,0, null);
    }

    public AccountReceivableFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<AccountReceivable> getSpecification() {
        return AccountReceivableSpecification.filter(this);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDate dateProcessed) {
        this.dateProcessed = dateProcessed;
    }
}
