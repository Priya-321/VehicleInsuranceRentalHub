package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
//handles  purchased policy info
}
