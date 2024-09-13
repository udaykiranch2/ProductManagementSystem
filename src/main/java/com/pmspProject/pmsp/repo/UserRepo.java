/**
 * Repository interface for the User entity.
 *
 * <p>Extends {@link JpaRepository} from Spring Data JPA, providing methods for performing CRUD operations on the database.
 *
 * <p>This interface is used by the service layer to interact with the User entity in the database.
 *
 * <p>Custom query methods are defined here:
 * <ul>
 *     <li>{@link #findByUsername(String)}: Retrieves a user based on the user's username.</li>
 * </ul>
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.repo;

import com.pmspProject.pmsp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}