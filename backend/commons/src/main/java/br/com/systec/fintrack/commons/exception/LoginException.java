package br.com.systec.fintrack.commons.exception;

public class LoginException extends BaseException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
