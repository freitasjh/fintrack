package br.com.systec.fintrack.administrator.bankAccount.enums;

import br.com.systec.fintrack.commons.exception.ObjectFoundException;

public enum AccountType {
    CURRENT_ACCOUNT(1),
    SAVINGS_ACCOUNT(2);

    private final int code;

    AccountType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AccountType valueOfCode(int code) {
        for(AccountType item : AccountType.values()) {
            if(item.getCode() == code){
                return item;
            }
        }

        throw new ObjectFoundException("Tipo de conta não encontrada");
    }

}
