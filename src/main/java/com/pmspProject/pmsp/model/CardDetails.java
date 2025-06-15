package com.pmspProject.pmsp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Audited
@Entity
public class CardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "CARD_NUMBER", nullable = false)
    private String cardNumber;

    @Column(name = "CARD_HOLDER_NAME")
    private String cardHolderName;

    @Column(name = "EXP_MONTH", nullable = false)
    private String expMonth;

    @Column(name = "EXP_YEAR", nullable = false)
    private String expYear;

    @Column(name = "CVC", nullable = false)
    private String cvc;

    @Column(name = "CARD_BRAND")
    private String cardBrand; // VISA, MASTERCARD, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}

