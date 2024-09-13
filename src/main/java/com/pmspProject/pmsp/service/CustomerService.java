/**
 * This class represents the CustomerService, which provides business logic for managing customer data.
 *
 * The CustomerService class interacts with the CustomerRepository to perform CRUD operations on the Customer entity.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Registers a new customer.
     *
     * @param customer The customer object to be registered.
     * @return The registered customer object.
     */
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return An Optional containing the customer object if found, otherwise an
     *         empty Optional.
     */
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Updates an existing customer.
     *
     * @param id       The ID of the customer to update.
     * @param customer The updated customer object.
     * @return The updated customer object.
     * @throws RuntimeException If the customer with the given ID does not exist.
     */
    public Customer updateCustomer(Long id, Customer customer) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customer.setId(id);
        return customerRepository.save(customer);
    }
}
