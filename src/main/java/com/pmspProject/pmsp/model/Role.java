/**
 * This class represents the Role entity in the database.
 * It uses Lombok's @Data annotation to automatically generate getters, setters, equals, hashCode, and toString methods.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Getters and setters
}
