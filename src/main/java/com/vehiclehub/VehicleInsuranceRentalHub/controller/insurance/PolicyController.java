package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;
import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.CustomerVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsurancePlan;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.PolicyService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.CustomerVehicleService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private CustomerVehicleService vehicleService;

    @Autowired
    private InsurancePlanService planService;

    @GetMapping("/list")
    public String listPolicies(Model model) {
        List<Policy> policies = policyService.getAllPolicies();
        model.addAttribute("policies", policies);
        return "policy/list";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Policy> policies;

        if (query.matches("\\d+")) { // ID Search
            try {
                Policy policy = policyService.getPolicyById(Integer.parseInt(query));
                policies = List.of(policy);
            } catch (NotFoundException e) {
                policies = policyService.getAllPolicies(); // fallback list
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else { // Name Search
            policies = policyService.searchByCustomerName(query);

            if (policies.isEmpty()) {
                policies = policyService.getAllPolicies(); // fallback list
                model.addAttribute("errorMessage", "No policies found for customer name: " + query);
            }
        }

        model.addAttribute("policies", policies);
        return "policy/list";
    }



    
    @GetMapping("/form")
    public String showPolicyForm(Model model) {
        model.addAttribute("policy", new Policy());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("plans", planService.getAllPlans());
        return "policy/form";
    }

    @PostMapping("/save")
    public String savePolicy(@ModelAttribute Policy policy,
                             @RequestParam int vehicle,
                             @RequestParam int plan) {

        CustomerVehicle selectedVehicle = vehicleService.getVehicleById(vehicle);
        InsurancePlan selectedPlan = planService.getPlanById(plan);

        policy.setVehicle(selectedVehicle);
        policy.setPlan(selectedPlan);

        if (policy.getStatus() == null || policy.getStatus().isEmpty())
            policy.setStatus("Active");

        policyService.savePolicy(policy);
        return "redirect:/policy/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePolicy(@PathVariable int id) {
        policyService.deletePolicy(id);
        return "redirect:/policy/list";
    }
}
