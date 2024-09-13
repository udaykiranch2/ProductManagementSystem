/**
 * This class represents the CustomerController which handles HTTP requests related to Customer operations.
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.controller;

import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Registers a new customer.
     *
     * @param customer The customer object to be registered.
     * @return ResponseEntity containing the registered customer.
     */
    // Register a new customer (Publicly accessible)
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) {
        Customer newCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(newCustomer);
    }

    /**
     * Retrieves customer details by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return ResponseEntity containing the customer details.
     */
    // Retrieve customer details (Authenticated customer)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerDetails(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Updates customer details by their ID.
     *
     * @param id       The ID of the customer to update.
     * @param customer The updated customer object.
     * @return ResponseEntity containing the updated customer.
     */

    // Update customer details (Authenticated customer)
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerDetails(@PathVariable Long id,
            @Valid @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }
}