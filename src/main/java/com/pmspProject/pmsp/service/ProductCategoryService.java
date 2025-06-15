package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.dto.ProductCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCategoryService {
    ProductCategoryDTO createCategory(ProductCategoryDTO categoryDTO);

    ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO categoryDTO);

    void deleteCategory(Long id);

    ProductCategoryDTO getCategoryById(Long id);

    Page<ProductCategoryDTO> getAllCategories(Pageable pageable);

    List<ProductCategoryDTO> getAllCategories();
}