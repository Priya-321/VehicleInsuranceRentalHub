package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsuranceCustomer;

public interface InsuranceCustomerRepository extends JpaRepository<InsuranceCustomer, Integer> {
//insurance customer management
	
	List<InsuranceCustomer> findByAgentId(int agentId);

}
