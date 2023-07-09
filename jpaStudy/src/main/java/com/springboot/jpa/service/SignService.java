package com.springboot.jpa.service;

import com.springboot.jpa.data.dto.SignInResultDto;
import com.springboot.jpa.data.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signup(String id, String password, String name, String role);
    SignInResultDto signin(String id, String password) throws RuntimeException;
}
