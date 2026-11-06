package com.payment_service.client;

import com.payment_service.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderFeignClient {

    @PutMapping("/api/v1/order/payment-success/{id}")
    void paymentSuccess(@PathVariable Long id);


}

