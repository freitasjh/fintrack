package br.com.systec.fintrack.category.exception;

import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.config.I18nTranslate;

public class CategoryNotFoundException extends ObjectNotFoundException {

    public CategoryNotFoundException() {
        super(I18nTranslate.toLocale("category.not.found"));
    }
}
