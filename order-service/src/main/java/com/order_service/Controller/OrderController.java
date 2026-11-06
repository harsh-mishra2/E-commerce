package com.order_service.Controller;

import com.order_service.entity.Order;
import com.order_service.entity.OrderStatus;
import com.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{cartUuid}")
    public Order placeOrder(@PathVariable String cartUuid){

        return orderService.placeOrder(cartUuid);

    }


    @PutMapping("/{id}/status")
    public void updateStatus(
            @PathVariable Integer id,
            @RequestParam OrderStatus status) {

        orderService.updateStatus(id, status);

    }


    @PutMapping("/payment-success/{id}")
    public ResponseEntity<String> paymentSuccess(@PathVariable Integer id) {

        orderService.paymentSuccess(id);

        return ResponseEntity.ok("Order Confirmed");
    }
}
