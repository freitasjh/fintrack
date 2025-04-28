package br.com.systec.fintrack.bankaccount.exception;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.config.I18nTranslate;
import org.springframework.http.HttpStatus;

public class BankAccountBalanceUpdateException extends BaseException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public BankAccountBalanceUpdateException() {
        super(I18nTranslate.toLocale("bank.account.balance.error"));
        this.httpStatus = HTTP_STATUS;
    }

    public BankAccountBalanceUpdateException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HTTP_STATUS;
    }
}
