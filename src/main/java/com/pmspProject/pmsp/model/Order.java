/**
 * This class represents the Order entity in the database.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order date is mandatory")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @NotBlank(message = "Order status is mandatory")
    private String status;

    @NotNull(message = "Total amount is mandatory")
    @Min(value = 0, message = "Total amount must be greater than or equal to 0")
    private Double totalAmount;

    // @NotNull(message = "Customer ID is mandatory")
    // private Long customerId;

    // @NotNull(message = "Product ID is mandatory")
    // private Long productId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id") // Explicitly map the foreign key
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id") // Explicitly map the foreign key
    private Product product;

    // Getters and setters
}