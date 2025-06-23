package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
//handles  purchased policy info
	
	List<Policy> findByVehicleId(int vehicleId);
	List<Policy> findByPlanId(int planId);
	List<Policy> findByStatus(String status);

}
