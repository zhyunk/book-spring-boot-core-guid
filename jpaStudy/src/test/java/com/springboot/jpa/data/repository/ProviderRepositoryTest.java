package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProviderRepositoryTest {
    
    @Autowired
    ProviderRepository providerRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @Test
    void relationshipTest() {
        // provider 등록
        Provider provider = Provider.builder()
                .name("땡땡 상사")
                .build();
        providerRepository.save(provider);

        // product에 provider 설정 1
        productRepository.save(
                Product.builder()
                        .name("키보드")
                        .price(200000)
                        .stock(200)
                        .provider(provider)
                        .build()
        );
        // product에 provider 설정 2
        productRepository.save(
                Product.builder()
                        .name("모니터")
                        .price(100000)
                        .stock(200)
                        .provider(provider)
                        .build()
        );
        // product에 provider 설정 3
        productRepository.save(
                Product.builder()
                        .name("마우스")
                        .price(20000)
                        .stock(200)
                        .provider(provider)
                        .build()
        );

        // provider의 id로 관계 지어진 product list 조회
        providerRepository
                .findById(provider.getId())
                .get()
                .getProductList()
                .forEach(product -> System.out.println(
                        "[ product ]___________🚩  " +
                                product
                        )
                );
    }
    
    

}