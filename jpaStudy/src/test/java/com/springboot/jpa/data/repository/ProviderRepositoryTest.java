package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.entity.Provider;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Test
    void cascadeTest() {
        Provider provider = savedProvider("새로운 공급업체");

        Product product1 = savedProduct("상품1", 1000, 1100);
        Product product2 = savedProduct("상품2", 2000, 2200);
        Product product3 = savedProduct("상품3", 3000, 3300);
        
        // 연관관계 설정
        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);
        
        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));
        
        // 여기서 영속성 전이가 수행 됨
        providerRepository.save(provider);
    }

    @Test
    @Transactional
    void orphanRemovalTest() {
        Provider provider = savedProvider("새로운 공급업체");

        Product product1 = savedProduct("상품1", 1000, 1100);
        Product product2 = savedProduct("상품2", 2000, 2200);
        Product product3 = savedProduct("상품3", 3000, 3300);

        // 연관관계 설정
        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        // 여기서 영속성 전이가 수행 됨
        providerRepository.saveAndFlush(provider);

        // 엔티티 저장 확인
        providerRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);

        // 고아 객체 생성
        Provider foundProvider = providerRepository.findById(1L).get();
        foundProvider.getProductList().remove(0);

        // 연관관계가 끊긴 상품 제거 확인
        providerRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);
    }

    private Product savedProduct(String name, int price, int stock) {
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

    private Provider savedProvider(String name) {
        return Provider.builder().name(name).build();
    }


}