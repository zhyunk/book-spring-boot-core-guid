package com.springboot.serverbox.controller;

import dto.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/crud-api")
public class CrudController {

    @GetMapping
    public String getName() {
        return "Flature";
    }

    @GetMapping(value = "/{variable}")
    public String getVariable(@PathVariable String variable) {
        return variable;
    }

    @GetMapping("/param")
    public String getNameWithParam(@RequestParam String name) {
        return "Hello. " + name + "!";
    }

    @PostMapping
    public ResponseEntity<MemberDto> getMamber(
            @RequestBody(required = false) MemberDto request,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String organization
    ) {
        System.out.println(request.getName());
        System.out.println(request.getEmail());
        System.out.println(request.getOrganization());

        return ResponseEntity.ok(
                MemberDto.builder()
                        .name(name)
                        .email(email)
                        .organization(organization).build()
        );
    }

    @PostMapping("/add-header")
    public ResponseEntity<MemberDto> addHeader(
            @RequestHeader("my-header") String header,
            @RequestBody MemberDto memberDto
    ) {
        System.out.println(header);

        return ResponseEntity.ok(memberDto);
    }
}
