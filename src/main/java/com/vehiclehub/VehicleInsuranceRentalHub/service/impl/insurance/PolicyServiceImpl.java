package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance.PolicyRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public Policy savePolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public Policy getPolicyById(int id) {
        Optional<Policy> optional = policyRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deletePolicy(int id) {
        policyRepository.deleteById(id);
    }

    @Override
    public List<Policy> getPoliciesByVehicleId(int vehicleId) {
        return policyRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<Policy> getPoliciesByPlanId(int planId) {
        return policyRepository.findByPlanId(planId);
    }

    @Override
    public List<Policy> getPoliciesByStatus(String status) {
        return policyRepository.findByStatus(status);
    }
}
