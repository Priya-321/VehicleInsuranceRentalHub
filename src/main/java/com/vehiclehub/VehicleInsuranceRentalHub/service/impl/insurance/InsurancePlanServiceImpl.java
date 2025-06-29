package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.ResourceNotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsurancePlan;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance.InsurancePlanRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsurancePlanServiceImpl implements InsurancePlanService {

    @Autowired
    private InsurancePlanRepository planRepository;

    @Override
    public InsurancePlan savePlan(InsurancePlan plan) {
        return planRepository.save(plan);
    }

    @Override
    public List<InsurancePlan> getAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public InsurancePlan getPlanById(int id) {
    	        return planRepository.findById(id)
    	             .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
    }

    @Override
    public void deletePlan(int id) {
        planRepository.deleteById(id);
    }
    
    @Override
    public List<InsurancePlan> getPlansByDuration(int months) {
        return planRepository.findByDurationInMonths(months);
    }

    @Override
    public List<InsurancePlan> getPlansBelowPremium(double premium) {
        return planRepository.findByPremiumLessThan(premium);
    }

    @Override
    public List<InsurancePlan> searchPlansByName(String keyword) {
        return planRepository.findByNameContainingIgnoreCase(keyword);
    }

}
