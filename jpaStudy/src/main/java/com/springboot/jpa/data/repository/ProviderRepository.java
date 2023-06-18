package com.springboot.jpa.data.repository;

import com.springboot.jpa.data.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
