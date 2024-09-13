/**
 * ProductService class manages product-related operations. It interacts with the ProductRepository and applies
 * security annotations to restrict access to certain methods based on user roles.
 *
 * @author uday
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new product.
     *
     * @param product the product to be created
     * @return the created product
     *         @PreAuthorize("hasRole('ADMIN')") Only administrators can access this
     *         method.
     */
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the product if found, otherwise an empty
     *         Optional
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Updates an existing product.
     *
     * @param id      the ID of the product to be updated
     * @param product the updated product
     * @return the updated product
     *         @PreAuthorize("hasRole('ADMIN')") Only administrators can access this
     *         method.
     */
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        product.setId(id);
        return productRepository.save(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     *           @PreAuthorize("hasRole('ADMIN')") Only administrators can access
     *           this method.
     */
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
