package br.com.systec.fintrack.financial.transaction.enums;

public enum TransactionType {
    INCOMING(1),EXPENSE(2);

    int code;

    TransactionType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TransactionType valueOfCode(int code) {
        for(TransactionType item : TransactionType.values()){
            if(item.getCode() == code){
                return item;
            }
        }

        throw new RuntimeException("Tipo não encontrado");
    }
}
