package com.example.ampersand02.exception;

import com.example.ampersand02.common.Constants;
import org.springframework.http.HttpStatus;

public class ErrorMessageException extends RuntimeException {

    private static final long serialVersionUID = 8904006977673383565L;

    private Constants.MESSAGE code;
    private Object params[];
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ErrorMessageException() {
    }

    public ErrorMessageException(Constants.MESSAGE code, Object...params) {
        this.code = code;
        this.params = params;
    }

    public ErrorMessageException(Constants.MESSAGE code, HttpStatus httpStatus, Object...params) {
        this.code = code;
        this.params = params;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Constants.MESSAGE getCode() {
        return code;
    }

    public Object[] getParams() {
        return params;
    }

}
