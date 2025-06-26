package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

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

    @GetMapping("/form")
    public String showClaimForm(Model model) {
        model.addAttribute("claim", new Claim());
        model.addAttribute("policies", policyService.getAllPolicies());
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
