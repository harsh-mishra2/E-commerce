package com.notificationService.service;



import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        System.out.println("Twilio From Number = " + fromNumber);
    }

    public String sendSms(String to, String message) {
        System.out.println("To = " + to);
        System.out.println("From = " + fromNumber);

        Message sms = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromNumber),
                message
        ).create();

        return sms.getSid();
    }
}