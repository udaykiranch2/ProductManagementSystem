package com.pmspProject.pmsp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.model.Order;
import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.repo.CustomerRepository;
import com.pmspProject.pmsp.repo.OrderRepository;
import com.pmspProject.pmsp.repo.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentGatewayService paymentGatewayService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder_Success() throws Exception {
        // Given
        Long customerId = 1L;
        Long productId = 1L;
        int quantity = 1;

        Customer mockCustomer = new Customer();
        mockCustomer.setId(customerId);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setStockQuantity(quantity);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(paymentGatewayService.processPayment(mockProduct.getPrice() * quantity, "paymentMethodId"))
                .thenReturn(true);

        // When
        Order order = orderService.createOrder(customerId, productId, quantity);

        // Then
        assertNotNull(order, "Order should not be null");
        assertEquals("PAID", order.getStatus(), "Order status should be 'PAID'");
        assertEquals(mockProduct.getPrice() * quantity, order.getTotalAmount(), "Order total amount should match");
    }

    @Test
    public void testGetOrdersByCustomerId() {
        // Given
        Long customerId = 1L;
        List<Order> mockOrders = new ArrayList<>();
        when(orderRepository.findByCustomerId(customerId)).thenReturn(mockOrders);

        // When
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);

        // Then
        assertEquals(mockOrders, orders, "Retrieved orders should match the mock orders");
    }

    @Test
    public void testProcessPayment_Success() throws Exception {
        // Given
        Long orderId = 1L;
        String paymentMethodId = "paymentMethodId";

        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setStatus("PENDING");
        mockOrder.setTotalAmount(100.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(paymentGatewayService.processPayment(mockOrder.getTotalAmount(), paymentMethodId)).thenReturn(true);

        // When
        Order order = orderService.processPayment(orderId, paymentMethodId);

        // Then
        assertNotNull(order, "Order should not be null");
        assertEquals("PAID", order.getStatus(), "Order status should be 'PAID'");
    }

    @Test
    public void testProcessPayment_Failure() throws Exception {
        // Given
        Long orderId = 1L;
        String paymentMethodId = "paymentMethodId";

        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setStatus("PENDING");
        mockOrder.setTotalAmount(100.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(paymentGatewayService.processPayment(mockOrder.getTotalAmount(), paymentMethodId)).thenReturn(false);

        // When
        Order order = orderService.processPayment(orderId, paymentMethodId);

        // Then
        assertNotNull(order, "Order should not be null");
        assertEquals("FAILED", order.getStatus(), "Order status should be 'FAILED'");
    }
}
