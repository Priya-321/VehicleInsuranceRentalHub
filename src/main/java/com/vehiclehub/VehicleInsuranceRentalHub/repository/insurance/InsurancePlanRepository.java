package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsurancePlan;

public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Integer> {
// handles the methods for insurance plans 
}
