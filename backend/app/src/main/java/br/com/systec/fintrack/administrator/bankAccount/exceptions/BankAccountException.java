package br.com.systec.fintrack.administrator.bankAccount.exceptions;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class BankAccountException extends BaseException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public BankAccountException() {
        super(I18nTranslate.toLocale("bank.account.exception"));
        this.httpStatus = HTTP_STATUS;
    }

    public BankAccountException(String message) {
        super(message);
        this.httpStatus = HTTP_STATUS;
    }

    public BankAccountException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HTTP_STATUS;
    }
}
