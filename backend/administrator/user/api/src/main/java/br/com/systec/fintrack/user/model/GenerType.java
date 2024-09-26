package br.com.systec.fintrack.user.model;

public enum GenerType {
    M("M", "Masculino"),
    F("F", "Feminino"),
    O("O", "Outro");

    final String code;
    final String description;

    GenerType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static GenerType valuesOfCode(String code) {
        for (GenerType item : GenerType.values()) {
            if(item.getCode().equalsIgnoreCase(code)){
                return item;
            }
        }

        throw new RuntimeException("Genero informado incorretamente");
    }

}
