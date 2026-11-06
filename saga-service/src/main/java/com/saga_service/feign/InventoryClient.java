package com.saga_service.feign;

import com.saga_service.dto.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/inventory/reserve")
    boolean reserve(@RequestBody OrderRequest request);

    @PostMapping("/inventory/release")
    String release(@RequestBody OrderRequest request);

}
