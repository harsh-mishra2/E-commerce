package com.saga_service.service;

import com.saga_service.dto.OrderRequest;
import com.saga_service.feign.InventoryClient;
import com.saga_service.feign.OrderClient;
import com.saga_service.feign.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SagaService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private PaymentClient paymentClient;

    public String placeOrder(OrderRequest request) {

        // Step 1 - Create Order
        Map<String, Object> order =
                (Map<String, Object>) orderClient.create(request);

        Long orderId =
                Long.valueOf(order.get("id").toString());

        // Step 2 - Reserve Inventory
        boolean reserved =
                inventoryClient.reserve(request);

        if (!reserved) {

            orderClient.update(orderId, "CANCELLED");

            return "Inventory Not Available";
        }

        // Step 3 - Payment
        boolean payment =
                paymentClient.pay(request);

        if (payment) {

            orderClient.update(orderId, "CONFIRMED");

            return "Order Successful";
        }

        // Compensation
        inventoryClient.release(request);

        orderClient.update(orderId, "CANCELLED");

        return "Payment Failed. Inventory Released.";

    }

}