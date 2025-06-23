package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.CustomerVehicle;

public interface CustomerVehicleRepository extends JpaRepository<CustomerVehicle, Integer> {
//customers insured vehicles
	
	List<CustomerVehicle> findByCustomerId(int customerId);
	CustomerVehicle findByRegistrationNumber(String registrationNumber);

}
