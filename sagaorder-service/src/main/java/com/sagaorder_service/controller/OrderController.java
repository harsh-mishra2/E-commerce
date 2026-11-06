package com.sagaorder_service.controller;


import com.sagaorder_service.dto.OrderRequest;
import com.sagaorder_service.entity.Order;
import com.sagaorder_service.entity.OrderStatus;
import com.sagaorder_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public Order create(@RequestBody OrderRequest request){

        return service.createOrder(request);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id){

        return service.getOrder(id);
    }

    @PutMapping("/{id}/{status}")
    public Order update(@PathVariable Long id,
                        @PathVariable OrderStatus status){

        return service.updateStatus(id,status);
    }
}
