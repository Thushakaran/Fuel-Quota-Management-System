package com.se.Fuel_Quota_Management_System.service;
import com.se.Fuel_Quota_Management_System.config.TwilioConfig;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioOTPService {

    @Autowired
    private final TwilioConfig twilioConfig;


    @Autowired
    public TwilioOTPService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
    }


    public String sendSMS(String smsNumber, String smsMessage) {
        if (smsNumber == null || smsMessage.isEmpty()) {
            throw new IllegalArgumentException("Destination number cannot be null or empty.");
        }
        if (smsNumber == null || smsMessage.isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty.");
        }

        try {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(smsNumber),
                    new com.twilio.type.PhoneNumber(twilioConfig.getTrialNumber()),
                    smsMessage
            ).create();
            log.info("Message sent successfully. SID: {}", message.getSid());
            return "Message sent successfully. SID: " + message.getSid();
        } catch (Exception e) {
            log.error("Error sending SMS: {}", e.getMessage(), e);
            throw new RuntimeException("Error sending SMS: " + e.getMessage());
        }

        }

    }
