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

import com.pmspProject.pmsp.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.UUID;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Audited
@Entity
@Builder
@Table(name = "USER")
public  class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE", unique = true)
    private Boolean isActive;

    @NotAudited
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Role> roles;
}
