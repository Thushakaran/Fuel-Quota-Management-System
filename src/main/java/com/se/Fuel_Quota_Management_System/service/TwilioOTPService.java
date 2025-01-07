package com.se.Fuel_Quota_Management_System.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioOTPService {

    @Value("${twilio.account_sid}")
    String account_sid;
    @Value("${twilio.auth_token}")
    String auth_token;
    @Value("${twilio.trial_number}")
    String trial_number;

    @PostConstruct
    private void setup() {
        Twilio.init(account_sid, auth_token);
    }

    public String sendSMS(String smsNumber, String smsMessage) {
        Message message = Message.creator(new PhoneNumber(smsNumber), new PhoneNumber(trial_number),smsMessage).create();
        System.out.println("SMS sent successfully.SID:"+message.getAccountSid());
        return smsNumber;
    }
}