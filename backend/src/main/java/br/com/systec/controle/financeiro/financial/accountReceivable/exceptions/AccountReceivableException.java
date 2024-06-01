package br.com.systec.controle.financeiro.AccountReceivable.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ReceiveException extends BaseException {

    public ReceiveException() {
    }

    public ReceiveException(String message) {
        super(message);
    }

    public ReceiveException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
