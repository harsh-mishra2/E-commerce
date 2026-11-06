package com.paymentsaga_service.service;



import com.paymentsaga_service.dto.PaymentRequest;
import com.paymentsaga_service.entity.Payment;
import com.paymentsaga_service.entity.PaymentStatus;
import com.paymentsaga_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Override
    public boolean pay(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setAmount(request.getAmount());

        if (request.getAmount() <= 1000) {

            payment.setStatus(PaymentStatus.SUCCESS);

            repository.save(payment);

            return true;
        }

        payment.setStatus(PaymentStatus.FAILED);

        repository.save(payment);

        return false;
    }
}