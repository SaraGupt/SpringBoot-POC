package com.example.poc.poc_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.poc.poc_app.entity.Order;
import com.example.poc.poc_app.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // ADD order to a customer
    @PostMapping("/customers/{customerId}/orders")
    public ResponseEntity<Order> addOrder(@PathVariable Long customerId,
                                                  @RequestBody Order order) {
        Order savedOrder = orderService.addOrderToCustomer(customerId, order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // GET all orders for a customer
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    // GET single order by ID
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    // UPDATE an order (replace items)
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId,
                                                     @RequestBody Order updatedOrder) {
        return ResponseEntity.ok(orderService.update(orderId, updatedOrder));
    }

    // DELETE an order
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }
}