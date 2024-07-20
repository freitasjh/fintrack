package br.com.systec.controle.financeiro.administrator.category.enums;

public enum CategoryType {
    INCOMING(0), EXPENSE(1);

    private int code;

    CategoryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CategoryType valueOfCode(Long code) {
        for(CategoryType item : CategoryType.values()){
            if(item.getCode() == code){
                return item;
            }
        }

        throw new RuntimeException("Codigo informado incorreto");
    }
}
