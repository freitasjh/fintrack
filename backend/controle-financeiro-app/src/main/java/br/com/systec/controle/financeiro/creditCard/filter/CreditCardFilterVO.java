package br.com.systec.controle.financeiro.creditCard.filter;

import br.com.systec.controle.financeiro.commons.filter.PageParamSearchVO;
import br.com.systec.controle.financeiro.creditCard.model.CreditCard;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardFilterVO extends PageParamSearchVO<CreditCard> {
    private Long bankAccountId;

    public CreditCardFilterVO(int limit, int page, String filter) {
        super(limit, page, filter);
    }

    @Override
    public Specification<CreditCard> getSpecification() {
        return CreditCardSpecification.find(this);
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
