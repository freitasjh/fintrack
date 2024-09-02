package br.com.systec.controle.financeiro.administrator.bankAccount.exceptions;

import br.com.systec.controle.financeiro.commons.exception.BaseException;
import br.com.systec.controle.financeiro.config.I18nTranslate;

public class BankAccountNotFound extends BaseException {

    public BankAccountNotFound() {
        super(I18nTranslate.toLocale("bank.account.not.found"));
    }

    public BankAccountNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
