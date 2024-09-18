package br.com.systec.controle.financeiro.administrator.bankAccount.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;

public class BankAccountNotFoundException extends BaseException {

    public BankAccountNotFoundException() {
        super(I18nTranslate.toLocale("bank.account.not.found"));
    }

    public BankAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
