package com.pmspProject.pmsp.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmspProject.pmsp.model.Customer;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    public void setUp() {
        // No setup needed for this test case
    }

    @Test
    public void testSaveCustomer() {
        // Given

        Customer customer = new Customer();
        customer.setFirstName("Jhon");
        customer.setLastName("Doe");
        customer.setEmail("jhon.doe@example.com");
        customer.setPhoneNumber("+1234567890");
        customer.setAddress("123 Main St");

        // When
        Customer savedCustomer = customerRepository.save(customer);

        // Then
        verify(customerRepository).save(customerArgumentCaptor.capture());
        assertEquals(customer, customerArgumentCaptor.getValue(), "Saved customer should match");
    }

    @Test
    public void testFindById() {
        // Given
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));

        // When
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);

        // Then
        assertTrue(foundCustomer.isPresent(), "Customer should be present");
        assertEquals(customer, foundCustomer.get(), "Found customer should match");
    }

    @Test
    public void testFindById_NotFound() {
        // Given
        Long customerId = 1L;
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // When
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);

        // Then
        assertFalse(foundCustomer.isPresent(), "Customer should not be present");
    }

    @Test
    public void testDeleteById() {
        // Given
        Long customerId = 1L;

        // When
        customerRepository.deleteById(customerId);

        // Then
        verify(customerRepository).deleteById(customerId);
        // No assertion needed as we are only verifying that the method is called
    }
}
