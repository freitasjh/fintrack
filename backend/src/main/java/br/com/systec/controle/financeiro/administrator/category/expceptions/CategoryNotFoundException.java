package br.com.systec.controle.financeiro.administrator.category.expceptions;

import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;

public class CategoryNotFoundException extends ObjectNotFoundException {

    public CategoryNotFoundException() {
        super(I18nTranslate.toLocale("category.not.found"));
    }
}
