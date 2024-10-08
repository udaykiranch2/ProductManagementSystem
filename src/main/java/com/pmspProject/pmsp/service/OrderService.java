/**
 * OrderService class manages order-related operations. It interacts with repositories for Customer, Order, and Product,
 * and utilizes a PaymentGatewayService for processing payments.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.service;

import com.pmspProject.pmsp.model.Customer;
import com.pmspProject.pmsp.model.Order;
import com.pmspProject.pmsp.model.Product;
import com.pmspProject.pmsp.repo.CustomerRepository;
import com.pmspProject.pmsp.repo.OrderRepository;
import com.pmspProject.pmsp.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentGatewayService paymentGatewayService; // This should be an interface for Stripe/PayPal integration

    /**
     * Creates a new order for a customer and a product.
     *
     * @param customerId the ID of the customer
     * @param productId  the ID of the product
     * @param quantity   the quantity of the product
     * @return the created order
     * @throws Exception if customer, product, or stock quantity is insufficient
     */
    public Order createOrder(Long customerId, Long productId, int quantity) throws Exception {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        Optional<Product> productOpt = productRepository.findById(productId);

        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found");
        }
        if (!productOpt.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOpt.get();
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Order order = new Order();
        order.setOrderDate(new java.util.Date());
        order.setStatus("PENDING");
        order.setTotalAmount(product.getPrice() * quantity);
        // order.setCustomerId(customerId);
        // order.setProductId(productId);

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        return orderRepository.save(order);
    }

    /**
     * Retrieves all orders for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return a list of orders for the customer
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    /**
     * Processes a payment for an order using the payment gateway.
     *
     * @param orderId         the ID of the order
     * @param paymentMethodId the ID of the payment method
     * @return the updated order
     * @throws Exception if the order or payment processing fails
     */
    public Order processPayment(Long orderId, String paymentMethodId) throws Exception {
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (!orderOpt.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        Order order = orderOpt.get();
        // Process payment using the payment gateway
        boolean paymentSuccess = paymentGatewayService.processPayment(order.getTotalAmount(), paymentMethodId);

        if (paymentSuccess) {
            order.setStatus("PAID");
        } else {
            order.setStatus("FAILED");
        }

        return orderRepository.save(order);
    }

    /**
     * Retrieves all orders for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return a list of orders for the customer
     * @deprecated This method is not implemented and should be removed.
     */
    public List<Order> getOrdersForCustomer(Long customerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrdersForCustomer'");
    }
}