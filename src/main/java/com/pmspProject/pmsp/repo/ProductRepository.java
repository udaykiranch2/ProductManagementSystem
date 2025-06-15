/**
 * Repository interface for the Product entity.
 *
 * <p>Extends {@link JpaRepository} from Spring Data JPA, providing methods for performing CRUD operations on the database.
 *
 * <p>This interface is used by the service layer to interact with the Product entity in the database.
 *
 * <p>Custom query methods can be defined here if needed.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.repo;

import com.pmspProject.pmsp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.hibernate.validator.constraints.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);
}