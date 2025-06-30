package com.vehiclehub.VehicleInsuranceRentalHub.repository.rental;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyVehicleRepository extends JpaRepository<CompanyVehicle, Integer> {
//This repository helps manage vehicles owned by the company.
	
	List<CompanyVehicle> findByStatus(String status);
	List<CompanyVehicle> findByModelContainingIgnoreCase(String model);

}
