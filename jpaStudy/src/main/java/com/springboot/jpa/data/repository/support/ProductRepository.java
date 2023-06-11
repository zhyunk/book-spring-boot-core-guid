package com.springboot.jpa.data.repository.support;

import com.springboot.jpa.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepositorySupport") // 다른 파일과 같은 이름으로, bean 충돌 방지를 위해 bean 이름 지정
public interface ProductRepository extends JpaRepository<Product, Long> ,
        ProductRepositoryCustom {
}
