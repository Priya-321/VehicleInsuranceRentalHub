package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Claim;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClaimRepository  extends JpaRepository<Claim, Integer>{
//manages claims of each policy
	
	List<Claim> findByPolicyId(int policyId);
	List<Claim> findByStatus(String status);

	

}
