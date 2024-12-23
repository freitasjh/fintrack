package br.com.systec.fintrack.creditcard.invoice.impl.filter;

import br.com.systec.fintrack.commons.filter.PageParamSearchVO;
import br.com.systec.fintrack.invoice.model.CreditCardInvoice;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardInvoiceFilterVO extends PageParamSearchVO<CreditCardInvoice> {

    private Long bankAccountId;

    public CreditCardInvoiceFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<CreditCardInvoice> getSpecification() {
        return null;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public CreditCardInvoiceFilterVO setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
        return this;
    }
}
