package com.se.Fuel_Quota_Management_System;

import com.se.Fuel_Quota_Management_System.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class FuelQuotaManagementSystemApplication {
	@Autowired
	private TwilioConfig twilioConfig;

	public static void main(String[] args) {
		SpringApplication.run(FuelQuotaManagementSystemApplication.class, args);
	}

}
