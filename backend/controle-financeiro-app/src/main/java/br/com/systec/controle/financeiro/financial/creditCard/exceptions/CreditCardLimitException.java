package br.com.systec.controle.financeiro.financial.creditCard.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;

public class CreditCardLimitException extends BaseException {

    public CreditCardLimitException() {
        super(I18nTranslate.toLocale("creditCard.limit.reached"));
    }

}
