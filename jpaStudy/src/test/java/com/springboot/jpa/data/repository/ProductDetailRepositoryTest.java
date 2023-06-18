package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.entity.ProductDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDetailRepositoryTest {

    @Autowired
    ProductDetailRepository productDetailRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Test
    void crTest() {
        Product product = Product
                .builder()
                .name("스프링 부트 JPA")
                .price(5000)
                .stock(500)
                .build();
        
        productRepository.save(product);

        ProductDetail productDetail = ProductDetail
                .builder()
                .product(product)
                .decription("스프링 부트와 JPA를 함께 볼 수 있는 책")
                .build();

        productDetailRepository.save(productDetail);

        System.out.println(
                "🚩______[ product from product detail ]_____  \n" +
                        productDetailRepository
                                .findById(productDetail.getId())
                                .get()
                                .getProduct()
        );

        System.out.println(
                "🚩 ______[ product detail from productDetail ]_____  \n" +
                        productDetailRepository
                                .findById(productDetail.getId())
                                .get()
        );
    }
    
}