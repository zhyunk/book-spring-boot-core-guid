package com.springboot.jpa.controller;

import com.springboot.jpa.data.dto.SignInResultDto;
import com.springboot.jpa.data.dto.SignUpResultDto;
import com.springboot.jpa.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final SignService service;

    @PostMapping("/sign-in")
    public SignInResultDto signIn(
            @RequestParam String id,
            @RequestParam String password
    ) throws RuntimeException {
        log.info("[signIn] 로그인을 시도하고 있습니다. id : {} , pw : ****", id);
        SignInResultDto dto = service.signin(id, password);

        if (dto.getCode() == 0)
            log.info("[signIn] 정상적으로 로그인 되었습니다. id : {} , token : {}", id, dto.getToken());

        return dto;
    }

    @PostMapping("/sign-up")
    public SignUpResultDto signUp(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String role
    ) {
        log.info(
                "[signUp] 회원가입을 수행합니다. id: {} , password: **** , name : {} , role: {}",
                id, name, role);

        SignUpResultDto dto = service.signup(id, password, name, role);

        log.info("[signUp] 회원가입을 완료했습니다. id : {}", id);

        return dto;
    }

    @GetMapping("/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        log.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
