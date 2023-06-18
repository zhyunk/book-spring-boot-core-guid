package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Category;
import com.springboot.jpa.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void relationshipTest() {
        Product product = Product.builder()
                .name("고량주")
                .price(2000)
                .stock(100)
                .build();
        productRepository.save(product);

        Category category = Category
                .builder()
                .code("S1")
                .name("주류")
                .build();
        category.getProducts().add(product);
        categoryRepository.save(category);

        // test
        categoryRepository
                .findById(1L)
                .get()
                .getProducts()
                .forEach(p -> System.out.println(
                        "[ p ]___________🚩  " +
                                p
                ));
    }

}