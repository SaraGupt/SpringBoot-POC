package com.example.poc.poc_app.service;

import com.example.poc.poc_app.entity.Customer;
import com.example.poc.poc_app.entity.Order;
import com.example.poc.poc_app.entity.OrderItem;
import com.example.poc.poc_app.exception.ResourceNotFoundException;
import com.example.poc.poc_app.repository.CustomerRepository;
import com.example.poc.poc_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Order addOrderToCustomer(Long customerId, Order order) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        order.setCustomer(customer);
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }
        customer.getOrders().add(order);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customer.getOrders();
    }

    public Order getById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Transactional
    public Order update(Long orderId, Order updatedOrder) {
        Order existingOrder = getById(orderId);
        existingOrder.setOrderNumber(updatedOrder.getOrderNumber());
        existingOrder.getItems().clear();
        if (updatedOrder.getItems() != null) {
            for (OrderItem item : updatedOrder.getItems()) {
                item.setOrder(existingOrder);
                existingOrder.getItems().add(item);
            }
        }
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void delete(Long orderId) {
        Order order = getById(orderId);
        orderRepository.delete(order);
    }
}