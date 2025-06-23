package com.vehiclehub.VehicleInsuranceRentalHub.service.insurance;

import java.util.List;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsurancePlan;


public interface InsurancePlanService {

	InsurancePlan savePlan(InsurancePlan plan);
    List<InsurancePlan> getAllPlans();
    InsurancePlan getPlanById(int id);
    void deletePlan(int id);
    
    List<InsurancePlan> getPlansByDuration(int months);
    List<InsurancePlan> getPlansBelowPremium(double premium);
    List<InsurancePlan> searchPlansByName(String keyword);

}
