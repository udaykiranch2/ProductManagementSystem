
package com.pmspProject.pmsp.service;

import java.util.List;

import com.pmspProject.pmsp.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(Long customerId, Long productId, int quantity) throws Exception;
    List<OrderResponse> getOrdersByCustomerId(Long customerId);
    OrderResponse processPayment(Long orderId, String paymentMethodId) throws Exception ;
}