package br.com.systec.controle.financeiro.administrator.user.exception;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class LoginUsernameValidateException extends BaseException {

    public LoginUsernameValidateException() {
        super(I18nTranslate.toLocale("login.username.exist"), HttpStatus.NOT_ACCEPTABLE);
    }
}
