package br.com.systec.fintrack.administrator.user.exception;

import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.config.I18nTranslate;

public class UserNotFoundException extends ObjectNotFoundException {

    public UserNotFoundException() {
        super(I18nTranslate.toLocale("user.not.found"));
    }
}
