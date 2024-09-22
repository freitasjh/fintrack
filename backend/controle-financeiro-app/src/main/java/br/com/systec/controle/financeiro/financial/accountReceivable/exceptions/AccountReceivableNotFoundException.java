package br.com.systec.controle.financeiro.financial.accountReceivable.exceptions;

import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;

public class AccountReceivableNotFoundException extends ObjectNotFoundException {

    public AccountReceivableNotFoundException() {
        super(I18nTranslate.toLocale("account.receivable.not.found"));
    }
}
