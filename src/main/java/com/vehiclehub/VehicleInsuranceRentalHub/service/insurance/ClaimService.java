package com.vehiclehub.VehicleInsuranceRentalHub.service.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Claim;


import java.util.List;

public interface ClaimService {

    Claim saveClaim(Claim claim);
    List<Claim> getAllClaims();
    Claim getClaimById(int id);
    void deleteClaim(int id);

    List<Claim> getClaimsByPolicyId(int policyId);
    List<Claim> getClaimsByStatus(String status);
    

   
}
