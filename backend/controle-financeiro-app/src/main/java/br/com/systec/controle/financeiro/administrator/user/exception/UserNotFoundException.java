package br.com.systec.controle.financeiro.administrator.user.exception;

import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;

public class UserNotFoundException extends ObjectNotFoundException {

    public UserNotFoundException() {
        super(I18nTranslate.toLocale("user.not.found"));
    }
}
