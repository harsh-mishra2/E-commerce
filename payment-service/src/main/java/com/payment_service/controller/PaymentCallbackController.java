package com.payment_service.controller;

import com.payment_service.client.OrderFeignClient;
import com.payment_service.dto.OrderEvent;
import com.payment_service.entity.Payment;
import com.payment_service.entity.PaymentStatus;
import com.payment_service.kafka.PaymentProducer;
import com.payment_service.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentCallbackController {

    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;
    private  PaymentProducer paymentProducer;

    public PaymentCallbackController(PaymentRepository paymentRepository,
                                     OrderFeignClient orderFeignClient, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.orderFeignClient = orderFeignClient;
        this.paymentProducer = paymentProducer;
    }

    @GetMapping("/payment/success")
    public String success(@RequestParam("session_id") String sessionId)
            throws StripeException {

        Session session = Session.retrieve(sessionId);

        Payment payment = paymentRepository
                .findByPaymentIntentId(session.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        OrderEvent event = new OrderEvent();
        event.setOrderId(payment.getId());
        event.setEmail("harshmishra9077@gmail.com");
        event.setMobile("+916392922230");
        event.setStatus("success");
        paymentProducer.sendOrderEvent(event);


        orderFeignClient.paymentSuccess(payment.getOrderId());

        return "Payment Successful";


    }

    @GetMapping("/payment/cancel")
    public String cancel() {
        return "Payment Cancelled";
    }
}