/**
 * This class represents the ProductController which handles HTTP requests related to Product operations.
 *@author uday
 * @since 1.0.0
 */

package com.pmspProject.pmsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmspProject.pmsp.dto.ProductDTO;
import com.pmspProject.pmsp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.hibernate.validator.constraints.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create a new product", description = "Creates a new product. Requires ADMIN role.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products.")
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Update product", description = "Updates an existing product. Requires ADMIN role.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @Operation(summary = "Delete product", description = "Deletes a product. Requires ADMIN role.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search products", description = "Search products by name or description.")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam String query,
            Pageable pageable) {
        return ResponseEntity.ok(productService.searchProducts(query, pageable));
    }
}
