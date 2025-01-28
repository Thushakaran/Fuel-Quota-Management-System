package com.se.Fuel_Quota_Management_System.exception;

public class InsufficientQuotaException extends RuntimeException{
    public InsufficientQuotaException(String message) {
        super(message);
    }
}
