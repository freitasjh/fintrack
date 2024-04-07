package br.com.systec.controle.financeiro.commons.exception;

public class ObjectFoundException extends BaseException {

    public ObjectFoundException() {
    }

    public ObjectFoundException(String message) {
        super(message);
    }

    public ObjectFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
