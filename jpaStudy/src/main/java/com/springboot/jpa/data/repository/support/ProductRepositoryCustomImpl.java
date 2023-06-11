package com.springboot.jpa.data.repository.support;

import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.entity.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    // QuerydslRepositorySupport 상속시 필수 구현 부분
    // 기본 생성자에서 도메인 클래스를 부모 클래스에 전달해야 한다.
    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findByName(String name) {
        QProduct product = QProduct.product;

        return from(product) // QuerydslRepositorySupport 제공 메서드. JPAQuery를 반환한다.
                .where(product.name.eq(name)) // 이하 QueryDSL(JPAQuery) 메서드
                .select(product)
                .fetch();
    }
}
