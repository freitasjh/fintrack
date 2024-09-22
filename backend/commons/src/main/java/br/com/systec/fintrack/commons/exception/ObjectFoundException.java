package br.com.systec.fintrack.commons.exception;

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
