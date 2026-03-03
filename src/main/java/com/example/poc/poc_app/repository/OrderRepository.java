package com.example.poc.poc_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.poc.poc_app.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
