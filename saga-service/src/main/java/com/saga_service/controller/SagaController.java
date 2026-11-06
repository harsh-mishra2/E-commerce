package com.saga_service.controller;

import com.saga_service.dto.OrderRequest;
import com.saga_service.service.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saga")
public class SagaController {

    @Autowired
    private SagaService sagaService;

    @PostMapping("/order")
    public String placeOrder(@RequestBody OrderRequest request){

        return sagaService.placeOrder(request);

    }

}