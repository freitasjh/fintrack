package br.com.systec.fintrack.administrator.user.exception;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class LoginUsernameValidateException extends BaseException {

    public LoginUsernameValidateException() {
        super(I18nTranslate.toLocale("login.username.exist"), HttpStatus.BAD_REQUEST);
    }
}
