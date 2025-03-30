package br.com.systec.fintrack.commons.filter;

import java.io.Serial;
import java.io.Serializable;

public abstract class FilterParamVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2816905370062429714L;
    protected String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
