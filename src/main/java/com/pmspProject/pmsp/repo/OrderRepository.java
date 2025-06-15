/**
 * Repository interface for the Order entity.
 *
 * <p>Extends {@link JpaRepository} from Spring Data JPA, providing methods for performing CRUD operations on the database.
 *
 * <p>This interface is used by the service layer to interact with the Order entity in the database.
 *
 * <p>Custom query methods are defined here:
 * <ul>
 *     <li>{@link #findByCustomerId(UUID)}: Retrieves a list of orders based on the customer's ID.</li>
 * </ul>
 *
 * <p>Additional custom query methods can be defined here if needed.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.repo;


import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmspProject.pmsp.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findByCustomerId(UUID customerId, Pageable pageable);
    // we can define custom query methods here if needed

}
