package com.springboot.jpa.data.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long number;

    private String name;
    private int price;
    private int stock;
}
