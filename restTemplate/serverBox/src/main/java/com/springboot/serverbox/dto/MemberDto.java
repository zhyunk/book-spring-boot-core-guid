package com.springboot.serverbox.dto;

import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private String name;
    private String email;
    private String organization;

}
