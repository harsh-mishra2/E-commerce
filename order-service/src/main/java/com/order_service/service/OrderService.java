package com.order_service.service;

import com.order_service.client.CartFeignClient;
import com.order_service.dto.CartDto;
import com.order_service.dto.CartItemDto;
import com.order_service.entity.Order;
import com.order_service.entity.OrderItem;
import com.order_service.entity.OrderStatus;
import com.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartFeignClient cartFeignClient;

    public OrderService(OrderRepository orderRepository,
                        CartFeignClient cartFeignClient) {

        this.orderRepository = orderRepository;
        this.cartFeignClient = cartFeignClient;
    }

    public Order placeOrder(String cartUuid){

        CartDto cart = cartFeignClient.getCart(cartUuid);

        Order order = new Order();

        order.setCartUuid(cartUuid);

        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for(CartItemDto item : cart.getItems()){

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(item.getProductId());

            orderItem.setBrandId(item.getBrandId());

            orderItem.setQuantity(item.getQuantity());

            orderItem.setPrice(item.getPrice());

            orderItem.setOrder(order);

            order.getItems().add(orderItem);

            total = total.add(
                    item.getPrice().multiply(
                            BigDecimal.valueOf(item.getQuantity())
                    )
            );
        }

        order.setTotalAmount(total);

// Initial status when order is created
        order.setStatus(OrderStatus.PAYMENT_PENDING);

        return orderRepository.save(order);

    }

    public void updateStatus(Integer id, OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        orderRepository.save(order);
    }


    public void paymentSuccess(Integer id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);
    }
}
