package com.se.Fuel_Quota_Management_System.service;

import com.se.Fuel_Quota_Management_System.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioOTPService {
    private static final Logger log = LoggerFactory.getLogger(TwilioOTPService.class);

    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioOTPService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public String sendSMS(String phoneNumber) {
        String messageBody = "The vehicle has registered."; // Predefined message

        try {
            System.out.println(new PhoneNumber(phoneNumber));
            System.out.println(new PhoneNumber(twilioConfig.getTrialNumber()));
            System.out.println(messageBody);
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber), // To
                    new PhoneNumber(twilioConfig.getTrialNumber()), // From
                    messageBody // Predefined message
            ).create();

            log.info("Message sent successfully. SID: {}", message.getSid());
            return "Message sent successfully. SID: " + message.getSid();
        } catch (Exception e) {
            log.error("Error sending SMS: {}", e.getMessage(), e);
            throw new RuntimeException("Error sending SMS: " + e.getMessage());
        }
    }
}
