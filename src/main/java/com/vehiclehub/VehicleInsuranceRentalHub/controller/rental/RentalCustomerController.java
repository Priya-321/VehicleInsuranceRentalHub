
package com.vehiclehub.VehicleInsuranceRentalHub.controller.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AgentService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rental-customer")
public class RentalCustomerController {

    @Autowired
    private RentalCustomerService rentalCustomerService;

    @Autowired
    private AgentService agentService;

    @GetMapping("/list")
    public String listCustomers(@RequestParam(name = "agentId", required = false) Integer agentId, Model model) {
        List<RentalCustomer> customers;

        if (agentId != null) {
            customers = rentalCustomerService.getCustomersByAgentId(agentId);
            model.addAttribute("selectedAgentId", agentId);
        } else {
            customers = rentalCustomerService.getAllCustomers();
            model.addAttribute("selectedAgentId", "");
        }

        List<Agent> agents = agentService.getAgentsByRole("RENTAL");
        model.addAttribute("agents", agents);
        model.addAttribute("customers", customers);
        return "rental_customer/list";
    }

   
    @GetMapping("/choose/{id}")
    public String chooseCustomer(@PathVariable int id) {
        return "redirect:/company-vehicle/select?customerId=" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<RentalCustomer> rentalCustomers;

        if (query.matches("\\d+")) {
            try {
                RentalCustomer customer = rentalCustomerService.getCustomerById(Integer.parseInt(query));
                rentalCustomers = List.of(customer);
            } catch (NotFoundException e) {
                rentalCustomers = rentalCustomerService.getAllCustomers();
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else {
            rentalCustomers = rentalCustomerService.searchByName(query);

            if (rentalCustomers.isEmpty()) {
                rentalCustomers = rentalCustomerService.getAllCustomers();
                model.addAttribute("errorMessage", "No rental customers found for name: " + query);
            }
        }

        model.addAttribute("customers", rentalCustomers);
        return "rental_customer/list";
    }

    @GetMapping("/form")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new RentalCustomer());
        model.addAttribute("agents", agentService.getAllAgents()); // for agent dropdown
        model.addAttribute("existingCustomers", rentalCustomerService.getAllCustomers()); // for dropdown list

        return "rental_customer/form"; 
    }


    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute RentalCustomer customer, @RequestParam int agent) {
        Agent selectedAgent = agentService.getAgentById(agent);
        customer.setAgent(selectedAgent);
        RentalCustomer savedCustomer = rentalCustomerService.saveCustomer(customer);
        return "redirect:/company-vehicle/select?customerId=" + savedCustomer.getId();

    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        rentalCustomerService.deleteCustomer(id);
        return "redirect:/rental-customer/list";
    }
}
