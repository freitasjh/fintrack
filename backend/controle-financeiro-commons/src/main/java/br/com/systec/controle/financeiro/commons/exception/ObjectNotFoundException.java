package br.com.systec.controle.financeiro.commons.exception;

public class ObjectNotFoundException extends BaseException{

    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
