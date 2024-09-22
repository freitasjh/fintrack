package br.com.systec.fintrack.security.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class SecurityTokenExpiredException extends BaseException {

    public SecurityTokenExpiredException(){
        super(I18nTranslate.toLocale("token.expired"), HttpStatus.UNAUTHORIZED);
    }
}
