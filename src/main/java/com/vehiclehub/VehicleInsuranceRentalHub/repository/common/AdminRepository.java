package com.vehiclehub.VehicleInsuranceRentalHub.repository.common;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	Optional<Admin> findByEmail(String email);

	List<Admin> findByNameContainingIgnoreCase(String name);

}
