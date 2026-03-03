package com.example.poc.poc_app.repository;
import com.example.poc.poc_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByCustomerName(String customerName);
}

