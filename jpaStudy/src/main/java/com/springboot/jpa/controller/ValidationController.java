package com.springboot.jpa.controller;

import com.springboot.jpa.data.dto.ValidRequestDto;
import com.springboot.jpa.data.group.ValidationGroup1;
import com.springboot.jpa.data.group.ValidationGroup2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/validation")
public class ValidationController {

    @PostMapping("/valid")
    public ResponseEntity<ValidRequestDto> checkValidationByValid(
            @Valid
            @RequestBody
            ValidRequestDto dto
    ) {

        log.info("🚩 ValidRequestDto ==> {}", dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/validated")
    public ResponseEntity<ValidRequestDto> checkValidationByValidated(
            @Validated
            @RequestBody
            ValidRequestDto dto
    ) {

        log.info("🚩 ValidatedRequestDto ==> {}", dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/validated/1")
    public ResponseEntity<ValidRequestDto> checkValidationByValidated1(
            @Validated(ValidationGroup1.class)
            @RequestBody
            ValidRequestDto dto
    ) {

        log.info("🚩 ValidatedRequestDto group 1 ==> {}", dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/validated/2")
    public ResponseEntity<ValidRequestDto> checkValidationByValidated2(
            @Validated(ValidationGroup2.class)
            @RequestBody
            ValidRequestDto dto
    ) {

        log.info("🚩 ValidatedRequestDto group 2 ==> {}", dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/validated/all")
    public ResponseEntity<ValidRequestDto> checkValidationByValidatedAll(
            @Validated({ValidationGroup1.class, ValidationGroup2.class})
            @RequestBody
            ValidRequestDto dto
    ) {

        log.info("🚩 ValidatedRequestDto group 1,2 ==> {}", dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

}
