package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
//handles  purchased policy info
	
	List<Policy> findByVehicleId(int vehicleId);
	List<Policy> findByPlanId(int planId);
	List<Policy> findByStatus(String status);
	
	@Query("SELECT p FROM Policy p WHERE LOWER(p.vehicle.customer.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Policy> findByCustomerNameContainingIgnoreCase(String name);
	
	@Query("SELECT p FROM Policy p WHERE LOWER(p.vehicle.customer.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Policy> findByCustomerName(@Param("name") String name);



}
