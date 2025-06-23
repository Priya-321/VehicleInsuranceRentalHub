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

    @GetMapping("/list")
    public String listAgents(Model model) {
        List<Agent> agents = agentService.getAllAgents();
        model.addAttribute("agents", agents);
        return "agent/list"; // templates/agent/list.html
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
