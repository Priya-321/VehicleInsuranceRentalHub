package com.vehiclehub.VehicleInsuranceRentalHub.controller.common;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping("/dashboard")
    public String agentDashboard() {
        return "agent/dashboard";
    }

    
    @GetMapping("/list")
    public String listAgents(@RequestParam(name = "role", required = false) String role, Model model) {
        List<Agent> agents;

        if (role != null && !role.isEmpty()) {
            agents = agentService.getAgentsByRole(role);
            model.addAttribute("filter", role);
        } else {
            agents = agentService.getAllAgents();
            model.addAttribute("filter", "");
        }

        model.addAttribute("agents", agents);
        return "agent/list";
    }


    @GetMapping("/form")
    public String showAgentForm(Model model) {
        model.addAttribute("agent", new Agent());
        return "agent/form"; // templates/agent/form.html
    }

    @PostMapping("/save")
    public String saveAgent(@ModelAttribute Agent agent) {
        agentService.saveAgent(agent);
        return "redirect:/agent/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAgent(@PathVariable int id) {
        agentService.deleteAgent(id);
        return "redirect:/agent/list";
    }
    
    

}
