package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Claim;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.Policy;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.ClaimService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private PolicyService policyService;

    @GetMapping("/list")
    public String listClaims(Model model) {
        List<Claim> claims = claimService.getAllClaims();
        model.addAttribute("claims", claims);
        return "claim/list";
    }

    @GetMapping("/search")
    public String searchClaims(@RequestParam("query") String query,
                               @RequestParam(value = "status", required = false) String status,
                               Model model) {
        List<Claim> claims = List.of();

        boolean found = false;

        if (query != null && query.matches("\\d+")) {
            try {
                claims = claimService.getClaimsByPolicyId(Integer.parseInt(query));
                found = !claims.isEmpty();
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Invalid policy ID: " + query);
            }
        }

        if (!found && status != null && !status.isEmpty()) {
            claims = claimService.getClaimsByStatus(status);
            found = !claims.isEmpty();
        }

        if (!found) {
            claims = claimService.getAllClaims(); // fallback
            if (status != null || query != null) {
                model.addAttribute("errorMessage", "No claims found for given search or filter.");
            }
        }

        model.addAttribute("claims", claims);
        return "claim/list";
    }


    
    @GetMapping("/form")
    public String showClaimForm(@RequestParam(name = "customerName", required = false) String customerName, Model model) {
        model.addAttribute("claim", new Claim());
        model.addAttribute("customerName", customerName);

        if (customerName != null && !customerName.trim().isEmpty()) {
            List<Policy> policies = policyService.searchByCustomerName(customerName.trim());
            model.addAttribute("policies", policies);

            if (policies.isEmpty()) {
                model.addAttribute("errorMessage", "No active policy found for customer: " + customerName);
            }
        }

        return "claim/form";
    }



    @PostMapping("/save")
    public String saveClaim(@ModelAttribute Claim claim, @RequestParam int policy) {
        Policy selectedPolicy = policyService.getPolicyById(policy);
        claim.setPolicy(selectedPolicy);

        if (claim.getStatus() == null || claim.getStatus().isEmpty())
            claim.setStatus("Pending");

        claimService.saveClaim(claim);
        return "redirect:/claim/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteClaim(@PathVariable int id) {
        claimService.deleteClaim(id);
        return "redirect:/claim/list";
    }
}
