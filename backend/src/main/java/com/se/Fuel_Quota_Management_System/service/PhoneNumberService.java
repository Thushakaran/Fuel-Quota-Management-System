package com.se.Fuel_Quota_Management_System.service;

import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService {

    private static final String SRI_LANKA_COUNTRY_CODE = "+94";

    public String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

        if (cleanedNumber.startsWith("94")) {
            return "+" + cleanedNumber;
        }

        if (cleanedNumber.startsWith("0")) {
            return SRI_LANKA_COUNTRY_CODE + cleanedNumber.substring(1);
        }

        return cleanedNumber;
    }
}

