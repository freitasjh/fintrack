package br.com.systec.fintrack.commons.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    protected HttpStatus httpStatus;

    public BaseException() {
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
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
