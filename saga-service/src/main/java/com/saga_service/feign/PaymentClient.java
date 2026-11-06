package com.saga_service.feign;


import com.saga_service.dto.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="PAYMENTSAGA-SERVICE")
public interface PaymentClient {

    @PostMapping("/payment/pay")
    boolean pay(@RequestBody OrderRequest request);

}
