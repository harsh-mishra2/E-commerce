package com.orderserviceexample.kafka;



import com.orderserviceexample.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String TOPIC = "order-events";

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent event) {

        System.out.println("Email  : " + event.getEmail());
        System.out.println("Mobile : " +event.getMobile());
        System.out.println("Status : " + event.getStatus());
        kafkaTemplate.send(TOPIC, event);

        System.out.println("Event Sent : " + event);
    }
}
