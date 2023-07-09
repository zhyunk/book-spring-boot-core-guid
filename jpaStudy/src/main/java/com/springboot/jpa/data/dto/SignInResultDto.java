package com.springboot.jpa.data.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SignInResultDto extends SignUpResultDto{

    private String token;

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token) {
        super(success, code, msg);
        this.token = token;
    }
}
