package com.saga_service.feign;



import com.saga_service.dto.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="SAGAORDER-SERVICE")
public interface OrderClient {

    @PostMapping("/order/create")
    Object create(@RequestBody OrderRequest request);

    @PutMapping("/order/{id}/{status}")
    Object update(@PathVariable Long id,
                  @PathVariable String status);

}
