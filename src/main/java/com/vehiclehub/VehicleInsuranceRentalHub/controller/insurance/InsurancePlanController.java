package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsurancePlan;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/insurance-plan")
public class InsurancePlanController {

    @Autowired
    private InsurancePlanService planService;

    @GetMapping("/list")
    public String listPlans(
            @RequestParam(name = "duration", required = false) Integer duration,
            @RequestParam(name = "maxPremium", required = false) Double maxPremium,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        List<InsurancePlan> plans;
        String message = null;

        if (duration != null) {
            plans = planService.getPlansByDuration(duration);
            model.addAttribute("filterType", "duration");
            if (plans.isEmpty()) message = "No plans found for the given duration.";
        } else if (maxPremium != null) {
            plans = planService.getPlansBelowPremium(maxPremium);
            model.addAttribute("filterType", "premium");
            if (plans.isEmpty()) message = "No plans found below the given premium.";
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            plans = planService.searchPlansByName(keyword);
            model.addAttribute("filterType", "keyword");
            if (plans.isEmpty()) message = "No plans found matching keyword: " + keyword;
        } else {
            plans = planService.getAllPlans();
        }

        model.addAttribute("plans", plans);
        model.addAttribute("errorMessage", message); //Add message to model
        model.addAttribute("totalCount", planService.getAllPlans().size()); // 👈 for View All
        return "insurance_plan/list";
    }

    
    @GetMapping("/form")
    public String showPlanForm(Model model) {
        model.addAttribute("plan", new InsurancePlan());
        return "insurance_plan/form";
    }

    @PostMapping("/save")
    public String savePlan(@ModelAttribute InsurancePlan plan) {
        planService.savePlan(plan);
        return "redirect:/insurance-plan/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePlan(@PathVariable int id) {
        planService.deletePlan(id);
        return "redirect:/insurance-plan/list";
    }
    
    @GetMapping("/select-plan")
    public String selectPlan(@RequestParam("customerId") int customerId,
                             @RequestParam("vehicleId") int vehicleId,
                             Model model) {

        model.addAttribute("plans", planService.getAllPlans());
        model.addAttribute("customerId", customerId);
        model.addAttribute("vehicleId", vehicleId);
        return "insurance_plan/select-plan";  // this is the new Thymeleaf page
    }

}
