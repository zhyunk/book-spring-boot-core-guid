package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name, Sort sort);
}
