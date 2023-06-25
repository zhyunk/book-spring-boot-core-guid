package com.springboot.jpa.data.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ValidRequestDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phoneNumber;

    @Min(value = 20) @Max(value = 40)
    private int age;

    @Size(min = 0, max = 40)
    private String description;

    @Positive // 양수 허용
    private int count;

    @AssertTrue
    private boolean booleanCheck;
}
