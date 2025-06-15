package com.pmspProject.pmsp.model;

import com.pmspProject.pmsp.audit.Auditable;
import com.pmspProject.pmsp.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Audited
@Table(name = "PAYMENTS")
public class Payment extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @DecimalMin("0.00")
    @Column(name="AMOUNT",nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private String failureReason;
}
