package br.com.systec.controle.financeiro.financial.accountReceivable.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class AccountReceivableException extends BaseException {

    public AccountReceivableException(String message) {
        super(message);
    }

    public AccountReceivableException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
