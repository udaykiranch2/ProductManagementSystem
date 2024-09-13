/**
 * Repository interface for the Customer entity.
 *
 * <p>Extends {@link JpaRepository} from Spring Data JPA, providing methods for performing CRUD operations on the database.
 *
 * <p>This interface is used by the service layer to interact with the Customer entity in the database.
 *
 * <p>Custom query methods can be defined here if needed.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmspProject.pmsp.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // we can define custom query methods here if needed

}
