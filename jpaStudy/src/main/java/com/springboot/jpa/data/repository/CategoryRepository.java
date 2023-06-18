package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
