package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsuranceCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsuranceCustomerService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<InsuranceCustomer> customers;

        if (query.matches("\\d+")) {
            try {
                InsuranceCustomer customer = insuranceCustomerService.getCustomerById(Integer.parseInt(query));
                customers = List.of(customer);
            } catch (NotFoundException e) {
                customers = insuranceCustomerService.getAllCustomers();
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else {
            customers = insuranceCustomerService.searchByName(query);
            if (customers.isEmpty()) {
                customers = insuranceCustomerService.getAllCustomers();
                model.addAttribute("errorMessage", "No insurance customers found for name: " + query);
            }
        }

        model.addAttribute("customers", customers);
        model.addAttribute("totalCount", insuranceCustomerService.getAllCustomers().size()); // 👈 Add total count
        return "insurance_customer/list";
    }


    
    @GetMapping("/form")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new InsuranceCustomer());
        model.addAttribute("agents", agentService.getAgentsByRole("INSURANCE"));
        model.addAttribute("customers", insuranceCustomerService.getAllCustomers());
        return "insurance_customer/form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute InsuranceCustomer customer, @RequestParam int agent, RedirectAttributes redirectAttributes) {
        Agent selectedAgent = agentService.getAgentById(agent);
        customer.setAgent(selectedAgent);
        InsuranceCustomer savedCustomer = insuranceCustomerService.saveCustomer(customer);

        // Redirect to vehicle form for policy flow
        redirectAttributes.addAttribute("customerId", savedCustomer.getId());
        return "redirect:/customer-vehicle/form";
    }


    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        insuranceCustomerService.deleteCustomer(id);
        return "redirect:/insurance-customer/list";
    }
}
