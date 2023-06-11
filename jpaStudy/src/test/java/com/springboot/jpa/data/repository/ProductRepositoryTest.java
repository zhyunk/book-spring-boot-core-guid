package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void sortingAndPagingTest() {
        // given
        Product product1 = Product.builder()
                .name("펜")
                .price(1000)
                .stock(100)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product product2 = Product.builder()
                .name("펜")
                .price(5000)
                .stock(300)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product product3 = Product.builder()
                .name("펜")
                .price(500)
                .stock(50)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        Product save1 = productRepository.save(product1);
        Product save2 = productRepository.save(product2);
        Product save3 = productRepository.save(product3);

        // then
        System.out.println(
                productRepository
                        .findByName(
                                "펜",
                                Sort.by(
                                        Sort.Order.asc("price")
                                )
                        )
                        .toString()
        );
        System.out.println(
                productRepository
                        .findByName(
                                "펜",
                                getSort()
                        )
                        .toString()
        );
    }

    private Sort getSort() {
        return Sort.by(
                Sort.Order.asc("price"),
                Sort.Order.desc("stock")
        );
    }
}
