package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsurancePlan;

public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Integer> {
// handles the methods for insurance plans 
	 List<InsurancePlan> findByDurationInMonths(int months);

	    List<InsurancePlan> findByPremiumLessThan(double maxPremium);

	    List<InsurancePlan> findByNameContainingIgnoreCase(String keyword);
}
