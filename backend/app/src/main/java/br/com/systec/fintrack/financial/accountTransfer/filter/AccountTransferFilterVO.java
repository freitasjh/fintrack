package br.com.systec.fintrack.financial.accountTransfer.filter;

import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import br.com.systec.fintrack.financial.accountTransfer.model.AccountTransfer;
import org.springframework.data.jpa.domain.Specification;

public class AccountTransferFilterVO extends PageParamSearchVO<AccountTransfer> {
    private Long bankAccountFromId;
    private Long bankAccountToId;

    public AccountTransferFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<AccountTransfer> getSpecification() {
        return AccountTransferSpecification.find(bankAccountFromId, bankAccountToId);
    }

    public Long getBankAccountFromId() {
        return bankAccountFromId;
    }

    public void setBankAccountFromId(Long bankAccountFromId) {
        this.bankAccountFromId = bankAccountFromId;
    }

    public Long getBankAccountToId() {
        return bankAccountToId;
    }

    public void setBankAccountToId(Long bankAccountToId) {
        this.bankAccountToId = bankAccountToId;
    }
}
