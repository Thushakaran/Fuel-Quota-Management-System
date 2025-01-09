package com.se.Fuel_Quota_Management_System.service;
import com.se.Fuel_Quota_Management_System.config.TwilioConfig;
import com.se.Fuel_Quota_Management_System.model.Vehicle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioOTPService {


    private final TwilioConfig twilioConfig;

    @Value("${twilio.account_sid}")
    String account_sid;
    @Value("${twilio.auth_token}")
    String auth_token;
    @Value("${twilio.trial_number}")
    String trial_number;
    @Autowired
    public TwilioOTPService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccount_sid(),twilioConfig.getAuth_token());
    }
    public void sendRegistrationConfirmation(Vehicle vehicle) {
        String messageBody = String.format(
                "Dear %s,\n\nYour vehicle %s has been successfully registered in the Fuel Quota Management System. " +
                        "Your unique QR code is ready for use: %s.\n\nThank you,\nFuel Quota Management Team",
                vehicle.getOwnerName(),
                vehicle.getVehicleNumber(),
                vehicle.getQrCode()
        );

        sendSMS(String.valueOf(vehicle.getVehicleOwner().getPhoneNumber()), messageBody);
    }
    public void sendFuelPumpingNotification(Vehicle vehicle, double fuelDispensed) {
        double remainingQuota = vehicle.getFuelQuota() - fuelDispensed;
        String messageBody = String.format(
                "Dear %s,\n\nFuel dispensing details:\n" +
                        "- Vehicle Number: %s\n" +
                        "- Fuel Dispensed: %.2f Liters\n" +
                        "- Remaining Quota: %.2f Liters\n\n" +
                        "Thank you for using our service.\nFuel Quota Management Team",
                vehicle.getOwnerName(),
                vehicle.getVehicleNumber(),
                fuelDispensed,
                remainingQuota
        );

        sendSMS(String.valueOf(vehicle.getVehicleOwner().getPhoneNumber()), messageBody);
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
                    new com.twilio.type.PhoneNumber(twilioConfig.getTrial_number()),
                    smsMessage
            ).create();
            return "Message sent successfully. SID: " + message.getSid();
        } catch (Exception e) {
            return "Error sending SMS: " + e.getMessage();
        }

    }
}