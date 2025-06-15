/**
 * This class represents the OrderController which handles HTTP requests related to Order operations.
 *@author uday
 *@since 1.0.0
 */
package com.pmspProject.pmsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.pmspProject.pmsp.dto.OrderDTO;
import com.pmspProject.pmsp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create a new order", description = "Creates a new order for a customer")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @Operation(summary = "Get all orders", description = "Retrieves a paginated list of all orders")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Update order", description = "Updates an existing order")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDTO));
    }

    @Operation(summary = "Delete order", description = "Deletes an order")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get orders by customer", description = "Retrieves all orders for a specific customer")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<OrderDTO>> getOrdersByCustomer(
            @PathVariable UUID customerId,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId, pageable));
    }

    @Operation(summary = "Process payment", description = "Processes payment for an order")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/payment")
    public ResponseEntity<OrderDTO> processPayment(
            @PathVariable UUID id,
            @RequestParam UUID paymentMethodId) {
        return ResponseEntity.ok(orderService.processPayment(id, paymentMethodId));
    }

    @Operation(summary = "Update order status", description = "Updates the status of an order")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}