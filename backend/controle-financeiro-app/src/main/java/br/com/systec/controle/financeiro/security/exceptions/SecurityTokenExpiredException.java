package br.com.systec.controle.financeiro.security.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class SecurityTokenExpiredException extends BaseException {

    public SecurityTokenExpiredException(){
        super(I18nTranslate.toLocale("token.expired"), HttpStatus.UNAUTHORIZED);
    }
}
