/**
 * This class represents the Order entity in the database.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.model;

import com.pmspProject.pmsp.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited
@Table(name = "PRODUCT")
public class Product extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotBlank(message = "Product name is mandatory")
    @Column(name = "NAME", columnDefinition = "VARCHAR(255)")
    private String name;

    @NotBlank(message = "Product description is mandatory")
    @Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
    @Column(name = "PRICE", precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Stock quantity is mandatory")
    @Min(value = 0, message = "Stock quantity must be greater than or equal to 0")
    @Column(name = "STOCK_QUANTITY")
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(name = "SKU", columnDefinition = "VARCHAR(255)")
    private String sku;

    @Column(name = "IMAGE_URL", columnDefinition = "VARCHAR(255)")
    private String imageUrl;

    // Getters and setters
}
