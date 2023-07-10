package com.springboot.jpa.controller;

import com.springboot.jpa.data.dto.ChangeProductNameDto;
import com.springboot.jpa.data.dto.ProductDto;
import com.springboot.jpa.data.dto.ProductResponseDto;
import com.springboot.jpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.jpa.common.InformationApi.HEADER_AUTH;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductResponseDto> getProduct(
            Long number,
            @RequestHeader(HEADER_AUTH) String header
    ) {
        ProductResponseDto productResponseDto = productService.getProduct(number);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }


    @PostMapping
    public ResponseEntity<ProductResponseDto> createProductName(
            @RequestBody ProductDto productDto,
            @RequestHeader(HEADER_AUTH) String header
    ) {
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }


    @PutMapping
    public ResponseEntity<ProductResponseDto> changeProductName(
            @RequestBody ChangeProductNameDto changeProductNameDto,
            @RequestHeader(HEADER_AUTH) String header
    ) throws Exception {
        ProductResponseDto productResponseDto = productService.changeProductName(
                changeProductNameDto.getNumber(),
                changeProductNameDto.getName()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(
            Long number,
            @RequestHeader(HEADER_AUTH) String header
    ) throws Exception {
        productService.deleteProduct(number);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("정상적으로 삭제되었습니다.");
    }
}
