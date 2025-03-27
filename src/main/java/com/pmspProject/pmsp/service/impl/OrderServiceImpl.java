/**
 * OrderService class manages order-related operations. It interacts with repositories for Customer, Order, and Product,
 * and utilizes a PaymentGatewayService for processing payments.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.service.impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmspProject.pmsp.dto.CustomerResponse;
import com.pmspProject.pmsp.dto.OrderRequest;
import com.pmspProject.pmsp.dto.OrderResponse;
import com.pmspProject.pmsp.dto.ProductResponse;
import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.model.Order;
import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.repo.CustomerRepository;
import com.pmspProject.pmsp.repo.OrderRepository;
import com.pmspProject.pmsp.repo.ProductRepository;
import com.pmspProject.pmsp.service.OrderService;
import com.pmspProject.pmsp.service.PaymentGatewayService;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @Override
    public OrderResponse createOrder(Long customerId, Long productId, int quantity) throws Exception {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        order.setTotalAmount(product.getPrice() * quantity);

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse processPayment(Long orderId, String paymentMethodId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        boolean paymentSuccess = paymentGatewayService.processPayment(order.getTotalAmount(), paymentMethodId);

        order.setStatus(paymentSuccess ? "PAID" : "FAILED");
        return mapToResponse(orderRepository.save(order));
    }

    // --- Mapping Methods ---

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());

        // Customer mapping
        Customer customer = order.getCustomer();
        if (customer != null) {
            CustomerResponse cr = new CustomerResponse();
            cr.setId(customer.getId());
            cr.setFirstName(customer.getFirstName());
            cr.setLastName(customer.getLastName());
            cr.setEmail(customer.getEmail());
            cr.setPhoneNumber(customer.getPhoneNumber());
            cr.setAddress(customer.getAddress());
            response.setCustomer(cr);
        }

        // Product mapping
        Product product = order.getProduct();
        if (product != null) {
            ProductResponse pr = new ProductResponse();
            pr.setId(product.getId());
            pr.setName(product.getName());
            pr.setDescription(product.getDescription());
            pr.setPrice(product.getPrice());
            pr.setStockQuantity(product.getStockQuantity());
            response.setProduct(pr);
        }

        return response;
    }

    private Order mapToEntity(OrderRequest request) {
        Order order = new Order();
        order.setOrderDate(request.getOrderDate());
        order.setStatus(request.getStatus());
        order.setTotalAmount(request.getTotalAmount());

        if (request.getCustomerId() != null) {
            customerRepository.findById(request.getCustomerId()).ifPresent(order::setCustomer);
        }
        if (request.getProductId() != null) {
            productRepository.findById(request.getProductId()).ifPresent(order::setProduct);
        }

        return order;
    }
}

