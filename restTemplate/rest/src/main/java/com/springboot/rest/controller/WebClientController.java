package com.springboot.rest.controller;

import com.springboot.rest.service.WebClientService;
import dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/web-client")
public class WebClientController {

    private final WebClientService service;

    @GetMapping
    public String getName() {
        return service.getName();
    }

    @GetMapping("/path-variable")
    public String getNameWithPathVariable() {
        return service.getNameWithPathVariable();
    }

    @GetMapping("/parameter")
    public String getNameWithParameter() {
        return service.getNameWithParameter();
    }

    @PostMapping
    public ResponseEntity<MemberDto> postDto() {
        return service.postWithParamAndBody();
    }

    @PostMapping("/header")
    public ResponseEntity<MemberDto> postWithHeader() {
        return service.postWithHeader();
    }
}
