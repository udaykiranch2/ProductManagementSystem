package com.pmspProject.pmsp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmspProject.pmsp.model.Order;
import com.pmspProject.pmsp.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place an order (Authenticated customer)
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Order> placeOrder(@Valid@RequestParam Long customerId, @Valid@RequestParam Long productId, @RequestParam int quantity) {
        try {
            Order order = orderService.createOrder(customerId, productId, quantity);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Retrieve orders for a customer (Authenticated customer)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersForCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersForCustomer(customerId);
        return ResponseEntity.ok(orders);
    }

    // Process payment for an order (Authenticated customer)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payForOrder(@PathVariable Long orderId,@Valid @RequestParam String paymentMethodId) {
        try {
            Order order = orderService.processPayment(orderId, paymentMethodId);
            return ResponseEntity.ok("Payment successful for Order ID: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}