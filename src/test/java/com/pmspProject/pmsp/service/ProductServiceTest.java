package com.pmspProject.pmsp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.repo.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        // Given
        Product newProduct = new Product();
        newProduct.setName("Product 1");

        // When
        when(productRepository.save(newProduct)).thenReturn(newProduct);

        // Then
        Product createdProduct = productService.createProduct(newProduct);
        assertNotNull(createdProduct, "Created product should not be null");
        assertEquals("Product 1", createdProduct.getName(), "Product name should match");
    }

    @Test
    public void testGetAllProducts() {
        // Given
        List<Product> mockProducts = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(mockProducts);

        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertEquals(mockProducts, products, "Retrieved products should match the mock products");
    }

    @Test
    public void testGetProductById_Found() {
        // Given
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        // When
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Then
        Optional<Product> productOpt = productService.getProductById(productId);
        assertTrue(productOpt.isPresent(), "Product should be present");
        assertEquals(mockProduct, productOpt.get(), "Retrieved product should match the mock product");
    }

    @Test
    public void testGetProductById_NotFound() {
        // Given
        Long productId = 1L;

        // When
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Then
        Optional<Product> productOpt = productService.getProductById(productId);
        assertFalse(productOpt.isPresent(), "Product should not be present");
    }

    @Test
    public void testUpdateProduct_Found() {
        // Given
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Product");

        // When
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        // Then
        Product updatedProductResult = productService.updateProduct(productId, updatedProduct);
        assertNotNull(updatedProductResult, "Updated product should not be null");
        assertEquals("Updated Product", updatedProductResult.getName(), "Product name should match");
    }

    @Test
    public void testUpdateProduct_NotFound() {
        // Given
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);

        // When
        when(productRepository.existsById(productId)).thenReturn(false);

        // Then
        assertThrows(RuntimeException.class, () -> productService.updateProduct(productId, updatedProduct),
                "RuntimeException should be thrown when product is not found");
    }

    @Test
    public void testDeleteProduct_Found() {
        // Given
        Long productId = 1L;

        // When
        when(productRepository.existsById(productId)).thenReturn(true);

        // Then
        productService.deleteProduct(productId);
        // No assertion needed as we are only verifying that no exception is thrown
    }

    @Test
    public void testDeleteProduct_NotFound() {
        // Given
        Long productId = 1L;

        // When
        when(productRepository.existsById(productId)).thenReturn(false);

        // Then
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId),
                "RuntimeException should be thrown when product is not found");
    }
}
