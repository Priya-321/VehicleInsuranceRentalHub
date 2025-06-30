package com.vehiclehub.VehicleInsuranceRentalHub.service.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;

import java.util.List;

public interface PolicyService {

    Policy savePolicy(Policy policy);
    List<Policy> getAllPolicies();
    Policy getPolicyById(int id);
    void deletePolicy(int id);

    List<Policy> searchByCustomerName(String name);
    List<Policy> getPoliciesByVehicleId(int vehicleId);
    List<Policy> getPoliciesByPlanId(int planId);
    List<Policy> getPoliciesByStatus(String status);
}
