package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Claim;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance.ClaimRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance.PolicyRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public Claim saveClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim getClaimById(int id) {
        return claimRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Claim with ID " + id + " not found."));
    }


    @Override
    public void deleteClaim(int id) {
        claimRepository.deleteById(id);
    }

    @Override
    public List<Claim> getClaimsByPolicyId(int policyId) {
        return claimRepository.findByPolicyId(policyId);
    }

    @Override
    public List<Claim> getClaimsByStatus(String status) {
        return claimRepository.findByStatus(status);
    }
    

    
}
