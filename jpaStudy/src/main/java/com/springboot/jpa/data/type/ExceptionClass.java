package com.springboot.jpa.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionClass {
    PRODUCT("Product")
    ;

    private final String exceptionClass;


    @Override
    public String toString() {
        return "💩 " + getExceptionClass() + " Exception. ";
    }
}
