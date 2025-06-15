package com.pmspProject.pmsp.model;


import com.pmspProject.pmsp.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Audited
@Entity
@Builder
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends Auditable<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
}
