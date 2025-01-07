package com.se.Fuel_Quota_Management_System.controller;
import com.se.Fuel_Quota_Management_System.model.SMSSendRequest;
import com.se.Fuel_Quota_Management_System.service.TwilioOTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/")
public class SMSRestController {

        @Autowired
        TwilioOTPService twilioOTPService;
        @PostMapping("/processSMS")
        public String processSMS(@RequestBody SMSSendRequest smsSendRequest)
        {
            log.info("processSMS started smsRequest"+ smsSendRequest.toString());
            return twilioOTPService.sendSMS(smsSendRequest.getDestinationSMSNumber(),smsSendRequest.getSmsMessage());
        }
    }

