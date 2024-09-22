package br.com.systec.fintrack.financial.creditCard.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;

public class CreditCardLimitException extends BaseException {

    public CreditCardLimitException() {
        super(I18nTranslate.toLocale("creditCard.limit.reached"));
    }

}
