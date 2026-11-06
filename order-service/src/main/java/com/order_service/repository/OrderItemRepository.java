package com.order_service.repository;

import com.order_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
