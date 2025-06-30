package com.vehiclehub.VehicleInsuranceRentalHub.repository.common;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository <Agent, Integer> {

	List<Agent> findByRole(String role);
	//Spring Data JPA will auto-generate the query based on method name.
	// Select * from agent where role = ?;
	List<Agent> findByNameContainingIgnoreCase(String name);
	Optional<Agent> findByEmail(String email);

}
