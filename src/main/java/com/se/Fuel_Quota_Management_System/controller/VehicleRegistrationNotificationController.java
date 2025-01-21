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
public class VehicleRegistrationNotificationController {

        @Autowired
        private TwilioOTPService twilioOTPService;
        @PostMapping("/processSMS")
        public String processSMS(@RequestBody SMSSendRequest smsSendRequest)
        {
            log.info("Received SMS request: Destination={}, Message={}",
                    smsSendRequest.getDestinationSMSNumber(), smsSendRequest.getSmsMessage());

            String response;
            try {
                response = twilioOTPService.sendSMS(smsSendRequest.getDestinationSMSNumber(), smsSendRequest.getSmsMessage());
                log.info("SMS sent successfully: {}", response);
            } catch (Exception e) {
                log.error("Error while processing SMS: {}", e.getMessage(), e);
                response = "Error sending SMS: " + e.getMessage();
            }
            return response;

        }
    }

