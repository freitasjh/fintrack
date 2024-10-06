package br.com.systec.fintrack.financial.received.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
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
