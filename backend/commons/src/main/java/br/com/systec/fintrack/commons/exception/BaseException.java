package br.com.systec.fintrack.commons.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1794160908523884477L;
    protected HttpStatus httpStatus;

    public BaseException() {
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(Throwable e) {
        super("Erro generico", e);
    }

    public BaseException(String message, Throwable e, HttpStatus httpStatus) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
