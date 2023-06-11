package com.springboot.jpa.data.dto;

import com.springboot.jpa.data.entity.Product;
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

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto
                .builder()
                .number(product.getNumber())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
