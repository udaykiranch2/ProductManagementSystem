package com.pmspProject.pmsp.model;

import com.pmspProject.pmsp.audit.Auditable;
import com.pmspProject.pmsp.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.envers.Audited;

import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited
@Table(name = "PAYMENT_METHODS")
public class PaymentMethod extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @NotBlank
    @Column(name = "METHOD_NAME", nullable = false)
    private PaymentType paymentType; // CREDIT_CARD, DEBIT_CARD, PAYPAL, etc.

    @Column(name = "PAYPAL_EMAIL")
    private String paypalEmail; // Only for PAYPAL type

    @Column(name = "BANK_ACCOUNT_DETAILS")
    private String bankAccountDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_details_id")
    private CardDetails cardDetails;

    private boolean isDefault = false;
}
