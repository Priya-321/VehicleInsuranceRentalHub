package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.CustomerVehicle;

public interface CustomerVehicleRepository extends JpaRepository<CustomerVehicle, Integer> {
//customers insured vehicles
}
