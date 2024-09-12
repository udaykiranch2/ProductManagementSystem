package com.pmspProject.pmsp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmspProject.pmsp.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // we can define custom query methods here if needed

}
