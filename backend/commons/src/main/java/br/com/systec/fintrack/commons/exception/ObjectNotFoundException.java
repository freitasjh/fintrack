package br.com.systec.fintrack.commons.exception;

import java.io.Serial;

public class ObjectNotFoundException extends BaseException{

    @Serial
    private static final long serialVersionUID = 1307242913769277756L;

    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
