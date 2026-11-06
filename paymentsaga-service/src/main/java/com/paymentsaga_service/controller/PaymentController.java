package com.paymentsaga_service.controller;

import com.paymentsaga_service.dto.PaymentRequest;
import com.paymentsaga_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/pay")
    public boolean pay(@RequestBody PaymentRequest request) {

        return service.pay(request);
    }
}