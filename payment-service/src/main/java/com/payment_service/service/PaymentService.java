package com.payment_service.service;

import com.payment_service.dto.PaymentRequest;
import com.payment_service.dto.PaymentResponse;
import com.payment_service.entity.Payment;
import com.payment_service.entity.PaymentStatus;
import com.payment_service.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public PaymentResponse makePayment(PaymentRequest request)
            throws StripeException {

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Order : " + request.getOrderId())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(request.getCurrency())
                        .setUnitAmount(
                                request.getAmount()
                                        .multiply(BigDecimal.valueOf(100))
                                        .longValue()
                        )
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8085/payment/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:8085/payment/cancel")
                        .addLineItem(lineItem)
                        .build();

        Session session = Session.create(params);

        Payment payment = new Payment();

        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentIntentId(session.getId());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        repository.save(payment);

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(session.getId());
        response.setCheckoutUrl(session.getUrl());
        response.setStatus("PENDING");

        return response;
    }
}