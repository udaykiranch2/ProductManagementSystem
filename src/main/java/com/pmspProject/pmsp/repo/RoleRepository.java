/**
 * Repository interface for the Role entity.
 *
 * <p>Extends {@link JpaRepository} from Spring Data JPA, providing methods for performing CRUD operations on the database.
 *
 * <p>This interface is used by the service layer to interact with the Role entity in the database.
 *
 * <p>Custom query methods are defined here:
 * <ul>
 *     <li>{@link #findByName(String)}: Retrieves a role based on the role's name.</li>
 * </ul>
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmspProject.pmsp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
