package br.com.systec.fintrack.financial.payment.filter;

import br.com.systec.fintrack.commons.exception.BaseException;

public enum AccountPaymentFilterType {
    ABERTO("0"),PAGOS("1"),TODOS("2");

    String code;

    AccountPaymentFilterType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AccountPaymentFilterType valueOfCode(String code) {
        for (AccountPaymentFilterType item : AccountPaymentFilterType.values()) {
            if(item.getCode().equals(code)){
                return item;
            }
        }

        throw new BaseException("Codigo do fitro invalido");
    }
}
