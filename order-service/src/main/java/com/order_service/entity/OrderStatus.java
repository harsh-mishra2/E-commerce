package com.order_service.entity;



public enum OrderStatus {
    PENDING,
    PAYMENT_PENDING,
    PAID,
    CANCELLED,
    SHIPPED,
    CONFIRMED, DELIVERED
}