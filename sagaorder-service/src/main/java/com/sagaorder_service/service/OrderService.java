package com.sagaorder_service.service;


import com.sagaorder_service.dto.OrderRequest;
import com.sagaorder_service.entity.Order;
import com.sagaorder_service.entity.OrderStatus;

public interface OrderService {

    Order createOrder(OrderRequest request);

    Order getOrder(Long id);

    Order updateStatus(Long id, OrderStatus status);

}
