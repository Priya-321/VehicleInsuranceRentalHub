package com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository  extends JpaRepository<Claim, Integer>{
//manages claims of each policy
}
