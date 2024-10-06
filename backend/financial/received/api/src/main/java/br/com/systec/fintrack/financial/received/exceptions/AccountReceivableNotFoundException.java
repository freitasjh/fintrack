package br.com.systec.fintrack.financial.received.exceptions;

import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.config.I18nTranslate;

public class AccountReceivableNotFoundException extends ObjectNotFoundException {

    public AccountReceivableNotFoundException() {
        super(I18nTranslate.toLocale("account.receivable.not.found"));
    }
}
