package com.notificationService.service;



import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.whatsapp.number}")
    private String whatsappNumber;

    public String sendWhatsApp(String to, String message) {

        Message whatsapp = Message.creator(
                new PhoneNumber("Whatsapp:" + to),
                new PhoneNumber(whatsappNumber),
                message
        ).create();

        return whatsapp.getSid();
    }
}