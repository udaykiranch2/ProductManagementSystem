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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Audited
@Table(name = "ORDERS")
public class Order extends Auditable<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull(message = "Order date is mandatory")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @NotBlank(message = "Order status is mandatory")
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull(message = "Total amount is mandatory")
    @DecimalMin(value = "0.0", message = "Total amount must be greater than or equal to 0.0")
    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id") // Explicitly map the foreign key
    private Customer customer;

    @NotAudited
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @NotAudited
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    // Getters and setters
}