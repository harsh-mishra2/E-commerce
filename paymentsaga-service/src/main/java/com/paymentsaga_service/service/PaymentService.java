package com.paymentsaga_service.service;


import com.paymentsaga_service.dto.PaymentRequest;

public interface PaymentService {

    boolean pay(PaymentRequest request);

}