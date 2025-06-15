package com.pmspProject.pmsp.repo;

import com.pmspProject.pmsp.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.hibernate.validator.constraints.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}