package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name, Sort sort);

    @Query("SELECT p FROM Product AS p WHERE p.name = :name")
    List<Product> findByName(@Param("name") String name);

    @Query("SELECT p.name, p.price, p.stock FROM Product p WHERE p.name = :name")
    List<Object[]> findByNameParam(@Param("name") String name);
}
