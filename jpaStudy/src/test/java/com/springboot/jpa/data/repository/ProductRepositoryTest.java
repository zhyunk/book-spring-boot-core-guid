package com.springboot.jpa.data.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.entity.QProduct;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void queryDslTest() {
        // QueryDSL을 사용하기 위해 JPAQuery 객체 사용
        // JPAQuery 객체는 entityManager를 활용해 생성한다.
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = query
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch(); // list 타입으로 반환받기 위해 fetch() 사용
             /*   조회 결과를 반환 받기 위한 메서드
                  fetch() : 리스트로 반환
                  fetchOne : 단 건의 조회 결과 반환
                  fetchFirst() : 여러 건의 조회 결과 중 1건 반환
                  fetchCount() : 조회 결과의 개수 반환
                  fetchResults() : 조회 결과 리스트와 개수를 포함한 QueryResults를 반환  */

        System.out.println("=============================================== size : " + productList.size());

        for (Product product: productList) {
            System.out.println("==========================================");
            System.out.println();
            System.out.println("product number = " + product.getNumber());
            System.out.println("product name = " + product.getName());
            System.out.println("product price = " + product.getPrice());
            System.out.println("product stock = " + product.getStock());
            System.out.println();
            System.out.println("==========================================");
        }

    }

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
