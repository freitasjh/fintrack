package br.com.systec.controle.financeiro.administrator.user.exception;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class LoginEmailValidationException extends BaseException {

    public LoginEmailValidationException() {
        super(I18nTranslate.toLocale("login.email.exist"), HttpStatus.BAD_REQUEST);
    }
}
