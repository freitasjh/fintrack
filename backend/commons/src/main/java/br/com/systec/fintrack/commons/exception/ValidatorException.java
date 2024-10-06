package br.com.systec.fintrack.commons.exception;

public class ValidatorException extends BaseException {

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}