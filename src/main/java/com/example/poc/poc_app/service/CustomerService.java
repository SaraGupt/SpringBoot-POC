package com.example.poc.poc_app.service;

import com.example.poc.poc_app.entity.Customer;
import com.example.poc.poc_app.entity.Order;
import com.example.poc.poc_app.entity.OrderItem;
import com.example.poc.poc_app.exception.ResourceNotFoundException;
import com.example.poc.poc_app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    // CREATE
    @Transactional
    public Customer save(Customer customer) {
        log.info("Saving customer: {}", customer.getCustomerName());

        if (customer.getOrders() != null) {
            for (Order order : customer.getOrders()) {
                order.setCustomer(customer);

                if (order.getItems() != null) {
                    for (OrderItem item : order.getItems()) {
                        item.setOrder(order);
                    }
                }
            }
        }

        return customerRepository.save(customer);
    }

    // READ ALL
    public List<Customer> getAll() {
        log.info("Fetching all customers");
        return customerRepository.findAll();
    }

    // READ BY ID
    public Customer getById(Long id) {
        log.info("Fetching customer with id: {}", id);
        return findCustomerById(id);
    }

    // DELETE
    @Transactional
    public void delete(Long id) {
        log.info("Deleting customer with id: {}", id);
        Customer customer = findCustomerById(id);
        customerRepository.delete(customer);
    }

    // UPDATE
    @Transactional
    public Customer update(Long id, Customer updatedCustomer) {
        log.info("Updating customer with id: {}", id);
        Customer existingCustomer = findCustomerById(id);
        existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
        existingCustomer.getOrders().clear();
        if (updatedCustomer.getOrders() != null) {
            for (Order order : updatedCustomer.getOrders()) {
                order.setCustomer(existingCustomer);
                if (order.getItems() != null) {
                    for (OrderItem item : order.getItems()) {
                        item.setOrder(order);
                    }
                }
                existingCustomer.getOrders().add(order);
            }
        }
        return customerRepository.save(existingCustomer);
    }

    private Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id));
    }
}