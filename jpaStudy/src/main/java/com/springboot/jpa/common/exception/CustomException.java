package com.springboot.jpa.common.exception;

import com.springboot.jpa.data.type.ExceptionClass;
import org.springframework.http.HttpStatus;

public class CustomException extends Exception{

    private ExceptionClass exceptionClass;
    private HttpStatus httpStatus;

    public CustomException(ExceptionClass exceptionClass, HttpStatus httpStatus, String message) {
        super(exceptionClass.toString() + message);
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public ExceptionClass getExceptionClass() {
        return exceptionClass;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }

    public String getHttpStatusType() {
        return httpStatus.getReasonPhrase();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
