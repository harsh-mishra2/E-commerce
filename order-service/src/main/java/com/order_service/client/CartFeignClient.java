package com.order_service.client;

import com.order_service.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CART-SERVICE")
public interface CartFeignClient {

    @GetMapping("/api/v1/cart/{uuid}")
    CartDto getCart(@PathVariable String uuid);

}
