package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Producer;
import com.springboot.jpa.data.entity.Product;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProducerRepositoryTest {
    @Autowired
    ProducerRepository producerRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @Transactional
    void relationshipTest() {

        Product product1 = saveProduct("두꺼비", 500, 1000);
        Product product2 = saveProduct("원숭이", 100, 2000);
        Product product3 = saveProduct("살빼지마 두꺼비ㅠ", 150, 3000);

        Producer producer1 = saveProducer("jinro");
        Producer producer2 = saveProducer("眞露");

        producer1.addProduct(product1);
        producer1.addProduct(product2);

        producer2.addProduct(product2);
        producer2.addProduct(product3);

        producerRepository.saveAll(Lists.newArrayList(producer1, producer2));
        producerRepository
                .findById(1L)
                .get()
                .getProducts()
                .forEach(product -> System.out.println(
                        "[ product ]___________🚩  " +
                                product
                ));
    }

    private Producer saveProducer(String name) {
        return producerRepository.save(Producer.builder().name(name).build());
    }

    private Product saveProduct(String name, int price, int stock) {
        return productRepository.save(
                Product.builder()
                        .name(name)
                        .price(price)
                        .stock(stock)
                        .build()
        );
    }


}