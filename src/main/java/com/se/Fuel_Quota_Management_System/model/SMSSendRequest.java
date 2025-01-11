package com.se.Fuel_Quota_Management_System.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SMSSendRequest{
private String destinationSMSNumber;
private String smsMessage;

}