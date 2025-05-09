package br.com.systec.fintrack.category.model;

public enum CategoryType {
    INCOMING(0), EXPENSE(1);

    private int code;

    CategoryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CategoryType valueOfCode(int code) {
        for(CategoryType item : CategoryType.values()){
            if(item.getCode() == code){
                return item;
            }
        }

        throw new RuntimeException("Codigo informado incorreto");
    }
}
