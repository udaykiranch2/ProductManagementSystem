/**
 * This class represents the User entity in the database.
 * It uses Lombok's @Data annotation to automatically generate getters, setters, equals, hashCode, and toString methods.
 *
 * The User entity is associated with multiple Role entities through a many-to-many relationship.
 * The relationship is defined using the @ManyToMany, @JoinTable, and @JoinColumn annotations.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
