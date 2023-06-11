package com.springboot.jpa.controller;

import com.springboot.jpa.data.dto.ChangeProductNameDto;
import com.springboot.jpa.data.dto.ProductDto;
import com.springboot.jpa.data.dto.ProductResponseDto;
import com.springboot.jpa.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "GET 메서드 예제")
    @GetMapping
    public ResponseEntity<ProductResponseDto> getProduct(Long number) {
        ProductResponseDto productResponseDto = productService.getProduct(number);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }


    @ApiOperation(value = "POST 메서드 예제")
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProductName(@RequestBody ProductDto productDto) {
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }


    @ApiOperation(value = "PUT 메서드 예제")
    @PutMapping
    public ResponseEntity<ProductResponseDto> changeProductName(
            @RequestBody ChangeProductNameDto changeProductNameDto
    ) throws Exception {
        ProductResponseDto productResponseDto = productService.changeProductName(
                changeProductNameDto.getNumber(),
                changeProductNameDto.getName()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productResponseDto);
    }

    @ApiOperation(value = "DELETE 메서드 예제")
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(Long number) throws Exception {
        productService.deleteProduct(number);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("정상적으로 삭제되었습니다.");
    }
}
