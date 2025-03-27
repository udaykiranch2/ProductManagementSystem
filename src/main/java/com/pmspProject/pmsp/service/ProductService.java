package com.pmspProject.pmsp.service;

import java.util.List;
import java.util.Optional;

import com.pmspProject.pmsp.dto.ProductRequest;
import com.pmspProject.pmsp.dto.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    Optional<ProductResponse> getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
