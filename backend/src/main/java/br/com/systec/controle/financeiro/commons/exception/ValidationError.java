package br.com.systec.controle.financeiro.commons.exception;

import br.com.systec.controle.financeiro.config.I18nTranslate;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private static final long serialVersionUID = 1L;
    private List<FieldMessage> list = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, I18nTranslate.toLocale(msg), timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return list;
    }


    public void addError(String fieldName, String messagem) {
        try{
            list.add(new FieldMessage(fieldName, I18nTranslate.toLocale(messagem)));
        }catch (Exception ignore){}

    }
}