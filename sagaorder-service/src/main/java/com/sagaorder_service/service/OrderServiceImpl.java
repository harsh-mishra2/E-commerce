package com.sagaorder_service.service;


import com.sagaorder_service.dto.OrderRequest;
import com.sagaorder_service.entity.Order;
import com.sagaorder_service.entity.OrderStatus;
import com.sagaorder_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository repository;

    @Override
    public Order createOrder(OrderRequest request) {

        Order order = new Order();

        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setAmount(request.getAmount());

        order.setStatus(OrderStatus.PENDING);

        return repository.save(order);
    }

    @Override
    public Order getOrder(Long id) {

        return repository.findById(id).orElse(null);
    }

    @Override
    public Order updateStatus(Long id, OrderStatus status) {

        Order order = repository.findById(id).orElse(null);

        order.setStatus(status);

        return repository.save(order);
    }
}
