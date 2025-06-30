package com.vehiclehub.VehicleInsuranceRentalHub.repository.rental;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalCustomerRepository extends JpaRepository<RentalCustomer,Integer>{

//manages all rental customer methods
	
	List<RentalCustomer> findByAgentId(int agentId);
	//select * from RentalCustomer where AgentId = ?;

	List<RentalCustomer> findByNameContainingIgnoreCase(String name);

}
