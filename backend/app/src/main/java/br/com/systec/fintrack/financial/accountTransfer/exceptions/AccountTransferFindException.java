package br.com.systec.fintrack.financial.accountTransfer.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class AccountTransferFindException extends BaseException {

    public AccountTransferFindException() {
        super(I18nTranslate.toLocale("account.transfer.find.error"));
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public AccountTransferFindException(String message, Throwable e, HttpStatus httpStatus) {
        super(message, e, httpStatus);
    }

    public AccountTransferFindException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public AccountTransferFindException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public AccountTransferFindException(String message, Throwable cause) {
        super(message, cause);
    }
}
