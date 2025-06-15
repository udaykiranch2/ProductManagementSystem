/**
 * This class represents the CustomerService, which provides business logic for managing customer data.
 *
 * The CustomerService class interacts with the CustomerRepository to perform CRUD operations on the Customer entity.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.dto.CustomerDTO;
import com.pmspProject.pmsp.exception.ResourceNotFoundException;
import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @CacheEvict(value = "customers", allEntries = true)
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = mapToEntity(customerDTO);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    @Cacheable(value = "customers")
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Cacheable(value = "customers", key = "#id")
    public CustomerDTO getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return mapToDTO(customer);
    }

    @CacheEvict(value = "customers", allEntries = true)
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        updateCustomerFromDTO(existingCustomer, customerDTO);
        existingCustomer.setUpdatedAt(LocalDateTime.now());
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapToDTO(updatedCustomer);
    }

    @CacheEvict(value = "customers", allEntries = true)
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
    }

    @Cacheable(value = "customers", key = "#query + #pageable.pageNumber + #pageable.pageSize")
    public Page<CustomerDTO> searchCustomers(String query, Pageable pageable) {
        return customerRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        query, query, query, pageable)
                .map(this::mapToDTO);
    }

    private Customer mapToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhone());
        customer.setAddress(dto.getAddress());
        return customer;
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhoneNumber());
        dto.setAddress(customer.getAddress());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        return dto;
    }

    private void updateCustomerFromDTO(Customer customer, CustomerDTO dto) {
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhone());
        customer.setAddress(dto.getAddress());
    }
}
