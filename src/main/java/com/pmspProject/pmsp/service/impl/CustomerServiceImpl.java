/**
 * This class represents the CustomerService, which provides business logic for managing customer data.
 *
 * The CustomerService class interacts with the CustomerRepository to perform CRUD operations on the Customer entity.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmspProject.pmsp.dto.CustomerRequest;
import com.pmspProject.pmsp.dto.CustomerResponse;
import com.pmspProject.pmsp.dto.OrderResponse;
import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.model.Order;
import com.pmspProject.pmsp.repo.CustomerRepository;
import com.pmspProject.pmsp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse registerCustomer(CustomerRequest request) {
        Customer customer = mapToEntity(request);
        Customer saved = customerRepository.save(customer);
        return mapToResponse(saved);
    }

    @Override
    public Optional<CustomerResponse> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        Customer updatedCustomer = mapToEntity(request);
        updatedCustomer.setId(id);
        Customer saved = customerRepository.save(updatedCustomer);
        return mapToResponse(saved);
    }

    // --- Mapping Methods ---

    private Customer mapToEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        // orderIds ignored – they belong to order creation
        return customer;
    }

    private CustomerResponse mapToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddress(customer.getAddress());

        if (customer.getOrders() != null) {
            response.setOrders(
                    customer.getOrders().stream()
                            .map(this::mapOrderToResponse)
                            .collect(Collectors.toList()));
        }

        return response;
    }

    private OrderResponse mapOrderToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        // Avoid circular reference by not setting customer here
        return response;
    }
}
