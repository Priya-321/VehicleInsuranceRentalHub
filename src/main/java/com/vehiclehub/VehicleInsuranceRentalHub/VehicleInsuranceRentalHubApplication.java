package com.vehiclehub.VehicleInsuranceRentalHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class VehicleInsuranceRentalHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleInsuranceRentalHubApplication.class, args);
		
		
	}

	
}
