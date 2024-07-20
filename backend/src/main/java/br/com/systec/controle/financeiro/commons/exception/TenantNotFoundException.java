package br.com.systec.controle.financeiro.commons.exception;

import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class TenantNotFoundException extends BaseException{
    public TenantNotFoundException() {
        super(I18nTranslate.toLocale("tenant.not.found"));
    }

    public TenantNotFoundException(String message, Throwable e, HttpStatus httpStatus) {
        super(message, e, httpStatus);
    }

    public TenantNotFoundException(String message) {
        super(message);
    }

    public TenantNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
