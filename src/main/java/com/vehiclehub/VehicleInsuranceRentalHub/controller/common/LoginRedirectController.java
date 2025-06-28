package com.vehiclehub.VehicleInsuranceRentalHub.controller.common;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AgentRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AdminRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRedirectController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AgentRepository agentRepo;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        String email = auth.getName();

        // Check admin table
        Optional<Admin> admin = adminRepo.findByEmail(email);
        if (admin.isPresent()) {
            return "admin/admin_dashboard";
        }

        // Check agent table
        Optional<Agent> agentOpt = agentRepo.findByEmail(email);
        if (agentOpt.isPresent()) {
            Agent agent = agentOpt.get();
            if ("RENTAL".equalsIgnoreCase(agent.getRole())) {
                return "agent/rental-dashboard";
            } else if ("INSURANCE".equalsIgnoreCase(agent.getRole())) {
                return "agent/insurance-dashboard";
            }
        }

        // Default fallback
        return "redirect:/login?error";
    }
    
    @GetMapping("/logout")
    public String loginPage() {
        return "login"; // Thymeleaf template name
    }
}
