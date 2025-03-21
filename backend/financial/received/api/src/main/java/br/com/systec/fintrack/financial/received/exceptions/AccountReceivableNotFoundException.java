package br.com.systec.fintrack.financial.received.exceptions;

import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.config.I18nTranslate;

import java.io.Serial;

public class AccountReceivableNotFoundException extends ObjectNotFoundException {

    @Serial
    private static final long serialVersionUID = -5741787259236246917L;

    public AccountReceivableNotFoundException() {
        super(I18nTranslate.toLocale("account.receivable.not.found"));
    }
}
