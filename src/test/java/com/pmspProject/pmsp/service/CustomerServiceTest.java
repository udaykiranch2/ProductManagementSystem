package com.pmspProject.pmsp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.repo.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testRegisterCustomer() {
        // Given
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("John");
        newCustomer.setLastName("John");

        // When
        when(customerRepository.save(newCustomer)).thenReturn(newCustomer);

        // Then
        Customer registeredCustomer = customerService.registerCustomer(newCustomer);
        assertNotNull(registeredCustomer, "Registered customer should not be null");
        assertEquals("John", registeredCustomer.getFirstName(), "Customer name should match");
        assertEquals("Doe", registeredCustomer.getFirstName(), "Customer name should match");

    }

    @Test
    public void testGetCustomerById_Found() {
        // Given
        Long customerId = 1L;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);

        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        // Then
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId);
        assertTrue(customerOpt.isPresent(), "Customer should be present");
        assertEquals(mockCustomer, customerOpt.get(), "Retrieved customer should match the mock customer");
    }

    @Test
    public void testGetCustomerById_NotFound() {
        // Given
        Long customerId = 1L;

        // When
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Then
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId);
        assertFalse(customerOpt.isPresent(), "Customer should not be present");
    }

    @Test
    public void testUpdateCustomer_Found() {
        // Given
        Long customerId = 1L;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);
        updatedCustomer.setFirstName("Jane Doe");

        // When
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // Then
        Customer updatedCustomerResult = customerService.updateCustomer(customerId, updatedCustomer);
        assertNotNull(updatedCustomerResult, "Updated customer should not be null");
        assertEquals("Jane Doe", updatedCustomerResult.getFirstName(), "Customer name should match");
    }

    @Test
    public void testUpdateCustomer_NotFound() {
        // Given
        Long customerId = 1L;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);

        // When
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // Then
        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(customerId, updatedCustomer),
                "RuntimeException should be thrown when customer is not found");
    }
}
