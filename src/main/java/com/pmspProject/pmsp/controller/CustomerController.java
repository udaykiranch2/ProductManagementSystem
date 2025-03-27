/**
 * This class represents the CustomerController which handles HTTP requests related to Customer operations.
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmspProject.pmsp.dto.CustomerRequest;
import com.pmspProject.pmsp.dto.CustomerResponse;
import com.pmspProject.pmsp.service.CustomerService;

import jakarta.validation.Valid;

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
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody CustomerRequest customer) {
        CustomerResponse newCustomer = customerService.registerCustomer(customer);
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
    public ResponseEntity<Optional<CustomerResponse>> getCustomerDetails(@PathVariable Long id) {
        Optional<CustomerResponse> customer = customerService.getCustomerById(id);
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
    public ResponseEntity<CustomerResponse> updateCustomerDetails(@PathVariable Long id,
            @Valid @RequestBody CustomerRequest customer) {
                CustomerResponse updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }
}