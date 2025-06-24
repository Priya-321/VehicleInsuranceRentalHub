package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsuranceCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsuranceCustomerService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/insurance-customer")
public class InsuranceCustomerController {

    @Autowired
    private InsuranceCustomerService insuranceCustomerService;

    @Autowired
    private AgentService agentService;

    @GetMapping("/list")
    public String listCustomers(@RequestParam(name = "agentId", required = false) Integer agentId, Model model) {
        List<InsuranceCustomer> customers;

        if (agentId != null) {
            customers = insuranceCustomerService.getCustomersByAgentId(agentId);
            model.addAttribute("selectedAgentId", agentId);
        } else {
            customers = insuranceCustomerService.getAllCustomers();
            model.addAttribute("selectedAgentId", "");
        }

        List<Agent> agents = agentService.getAgentsByRole("INSURANCE");
        model.addAttribute("agents", agents);
        model.addAttribute("customers", customers);
        return "insurance_customer/list";
    }

    @GetMapping("/form")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new InsuranceCustomer());
        model.addAttribute("agents", agentService.getAgentsByRole("INSURANCE"));
        return "insurance_customer/form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute InsuranceCustomer customer, @RequestParam int agent) {
        Agent selectedAgent = agentService.getAgentById(agent);
        customer.setAgent(selectedAgent);
        insuranceCustomerService.saveCustomer(customer);
        return "redirect:/insurance-customer/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        insuranceCustomerService.deleteCustomer(id);
        return "redirect:/insurance-customer/list";
    }
}
