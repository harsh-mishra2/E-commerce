package com.payment_service.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private Long orderId;

    private BigDecimal amount;

    private String currency;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
// getters setters

}