package com.notificationService.kafka;




import com.notificationService.dto.OrderEvent;
import com.notificationService.service.EmailService;
import com.notificationService.service.SmsService;
import com.notificationService.service.WhatsAppService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotificationConsumer {

private EmailService emailService;
private SmsService smsService;
private WhatsAppService whatsAppService;

    public NotificationConsumer(EmailService emailService, SmsService smsService, WhatsAppService whatsAppService) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.whatsAppService = whatsAppService;
    }

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-group"
    )
    public void consume(OrderEvent event) {

        System.out.println("Event Received : " + event.getEmail());
        System.out.println("Status = " + event.getStatus());
        if (Objects.equals(event.getStatus(), "success")) {
            emailService.sendEmail(event.getEmail(), "Transaction Completed", "Yours order is placed successfully:"+event.getOrderId());
            try {
                smsService.sendSms(event.getMobile(), "Transaction Completed");
            } catch (Exception e) {
                System.err.println("SMS failed: " + e.getMessage());
            }

            try {
                whatsAppService.sendWhatsApp(event.getMobile(), "Transaction Completed");
            } catch (Exception e) {
                System.err.println("WhatsApp failed: " + e.getMessage());
            }
        }else{
            emailService.sendEmail(event.getEmail(), "Transaction Failed", "Yours order is placed failed:"+event.getOrderId());
        }


        String subject = "Order Status Update";

        String body =
                "Order ID : " + event.getOrderId() +
                        "\nStatus : " + event.getStatus();


    }
}
