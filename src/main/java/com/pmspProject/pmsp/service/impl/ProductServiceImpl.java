package com.pmspProject.pmsp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.pmspProject.pmsp.dto.ProductRequest;
import com.pmspProject.pmsp.dto.ProductResponse;
import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.repo.ProductRepository;
import com.pmspProject.pmsp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new product.
     *
     * @param request the product request data
     * @return the created product as a response DTO
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = mapToEntity(request);
        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all product responses
     */
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the product response if found
     */
    @Override
    public Optional<ProductResponse> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponse);
    }

    /**
     * Updates an existing product.
     *
     * @param id      the ID of the product to be updated
     * @param request the updated product data
     * @return the updated product response
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        Product productToUpdate = mapToEntity(request);
        productToUpdate.setId(id);

        Product updatedProduct = productRepository.save(productToUpdate);
        return mapToResponse(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    /**
     * Maps ProductRequest to Product entity.
     *
     * @param request the product request
     * @return the product entity
     */
    private Product mapToEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        return product;
    }

    /**
     * Maps Product entity to ProductResponse.
     *
     * @param product the product entity
     * @return the product response DTO
     */
    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        return response;
    }
}
