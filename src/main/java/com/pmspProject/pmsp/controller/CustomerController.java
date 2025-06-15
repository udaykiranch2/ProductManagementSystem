/**
 * This class represents the CustomerController which handles HTTP requests related to Customer operations.
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.controller;

import com.pmspProject.pmsp.dto.CustomerDTO;
import com.pmspProject.pmsp.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.hibernate.validator.constraints.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Register a new customer", description = "Registers a new customer in the system")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerDTO> registerCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.registerCustomer(customerDTO));
    }

    @Operation(summary = "Get all customers", description = "Retrieves a paginated list of all customers")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAllCustomers(pageable));
    }

    @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their ID")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(summary = "Update customer", description = "Updates an existing customer's details")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
    }

    @Operation(summary = "Delete customer", description = "Deletes a customer")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search customers", description = "Search customers by name or email")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<Page<CustomerDTO>> searchCustomers(
            @RequestParam String query,
            Pageable pageable) {
        return ResponseEntity.ok(customerService.searchCustomers(query, pageable));
    }
}