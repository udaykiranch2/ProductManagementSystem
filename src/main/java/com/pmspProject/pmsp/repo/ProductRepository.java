package com.pmspProject.pmsp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmspProject.pmsp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can define custom query methods here if needed
}