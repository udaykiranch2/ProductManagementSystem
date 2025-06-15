/**
 * OrderService class manages order-related operations. It interacts with repositories for Customer, Order, and Product,
 * and utilizes a PaymentGatewayService for processing payments.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.dto.OrderDTO;
import com.pmspProject.pmsp.exception.ResourceNotFoundException;
import com.pmspProject.pmsp.model.Order;
import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.model.PaymentMethod;
import com.pmspProject.pmsp.repo.OrderRepository;
import com.pmspProject.pmsp.repo.CustomerRepository;
import com.pmspProject.pmsp.repo.PaymentMethodRepository;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = mapToEntity(orderDTO);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Cacheable(value = "orders")
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapToDTO(order);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO updateOrder(UUID id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        updateOrderFromDTO(existingOrder, orderDTO);
        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToDTO(updatedOrder);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", "id", id);
        }
        orderRepository.deleteById(id);
    }

    @Cacheable(value = "orders", key = "#customerId + #pageable.pageNumber + #pageable.pageSize")
    public Page<OrderDTO> getOrdersByCustomerId(UUID customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable)
                .map(this::mapToDTO);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO processPayment(UUID orderId, UUID paymentMethodId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", "id", paymentMethodId));

        // Process payment logic here
        order.setStatus("PAID");
        Order updatedOrder = orderRepository.save(order);
        return mapToDTO(updatedOrder);
    }

    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO updateOrderStatus(UUID id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return mapToDTO(updatedOrder);
    }

    private Order mapToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", dto.getCustomerId()));
            order.setCustomer(customer);
        }

        return order;
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer() != null ? order.getCustomer().getId() : null);
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        return dto;
    }

    private void updateOrderFromDTO(Order order, OrderDTO dto) {
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        order.setTotalAmount(dto.getTotalAmount());

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", dto.getCustomerId()));
            order.setCustomer(customer);
        }
    }
}