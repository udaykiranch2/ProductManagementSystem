/**
 * ProductService class manages product-related operations. It interacts with the ProductRepository and applies
 * security annotations to restrict access to certain methods based on user roles.
 *
 * @author uday
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.dto.ProductDTO;
import com.pmspProject.pmsp.exception.ResourceNotFoundException;
import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.model.ProductCategory;
import com.pmspProject.pmsp.repo.ProductCategoryRepository;
import com.pmspProject.pmsp.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.validator.constraints.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    /**
     * Creates a new product.
     *
     * @param productDTO the product DTO to be created
     * @return the created product DTO
     */
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    /**
     * Retrieves all products.
     *
     * @param pageable the pagination information
     * @return a page of product DTOs
     */
    @Cacheable(value = "products")
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return the product DTO
     */
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return mapToDTO(product);
    }

    /**
     * Updates an existing product.
     *
     * @param id         the ID of the product to be updated
     * @param productDTO the updated product DTO
     * @return the updated product DTO
     */
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        updateProductFromDTO(existingProduct, productDTO);
        Product updatedProduct = productRepository.save(existingProduct);
        return mapToDTO(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Searches for products based on a query.
     *
     * @param query    the search query
     * @param pageable the pagination information
     * @return a page of product DTOs
     */
    @Cacheable(value = "products", key = "#query + #pageable.pageNumber + #pageable.pageSize")
    public Page<ProductDTO> searchProducts(String query, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable)
                .map(this::mapToDTO);
    }

    private Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSku(dto.getSku());
        product.setImageUrl(dto.getImageUrl());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", dto.getCategoryId()));
            product.setCategory(category);
        }

        return product;
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .sku(product.getSku())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }

    private void updateProductFromDTO(Product product, ProductDTO dto) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSku(dto.getSku());
        product.setImageUrl(dto.getImageUrl());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", dto.getCategoryId()));
            product.setCategory(category);
        }
    }
}
