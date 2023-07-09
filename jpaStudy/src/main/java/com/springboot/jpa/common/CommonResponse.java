package com.springboot.jpa.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonResponse {

    SUCCESS(0, "Success"), FAIL(-1, "Fail");

    private final int code;
    private final String msg;
}
