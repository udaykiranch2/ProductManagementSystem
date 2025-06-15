/**
 * This class represents the Role entity in the database.
 * It uses Lombok's @Data annotation to automatically generate getters, setters, equals, hashCode, and toString methods.
 *
 * @author uday
 * @since 1.0
 */
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
@Table(name = "roles")
public class Role extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name="DESCRIPTION")
    private String description;
    // Getters and setters
}
