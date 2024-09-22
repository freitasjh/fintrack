package br.com.systec.controle.financeiro.administrator.bankAccount.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
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
