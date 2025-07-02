package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.CustomerVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsuranceCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.CustomerVehicleService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsuranceCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer-vehicle")
public class CustomerVehicleController {

    @Autowired
    private CustomerVehicleService vehicleService;

    @Autowired
    private InsuranceCustomerService customerService;

    @GetMapping("/list")
    public String listVehicles(Model model) {
        List<CustomerVehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "customer_vehicle/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<CustomerVehicle> vehicles;

        if (query.matches("\\d+")) { // ID Search
            try {
                CustomerVehicle vehicle = vehicleService.getVehicleById(Integer.parseInt(query));
                vehicles = List.of(vehicle);
            } catch (NotFoundException e) {
                vehicles = vehicleService.getAllVehicles(); // fallback list
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else { // Name Search
            vehicles = vehicleService.searchByName(query);

            if (vehicles.isEmpty()) {
                vehicles = vehicleService.getAllVehicles(); // fallback list
                model.addAttribute("errorMessage", "No customer vehicles found for name: " + query);
            }
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("totalCount", vehicleService.getAllVehicles().size()); // 👈 Add this
        return "customer_vehicle/list";
    }



    @GetMapping("/form")
    public String showVehicleForm(@RequestParam(required = false) Integer customerId, Model model) {
        CustomerVehicle vehicle = new CustomerVehicle();

        if (customerId != null) {
            InsuranceCustomer customer = customerService.getCustomerById(customerId);
            vehicle.setCustomer(customer);

            // Show existing vehicles of this customer
            List<CustomerVehicle> customerVehicles = vehicleService.getVehiclesByCustomerId(customerId);
            model.addAttribute("customerVehicles", customerVehicles);
        }

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customerId", customerId);
        return "customer_vehicle/form";
    }


    @PostMapping("/save")
    public String saveVehicle(@ModelAttribute CustomerVehicle vehicle,
                              @RequestParam int customer,
                              RedirectAttributes redirectAttributes) {
        InsuranceCustomer selectedCustomer = customerService.getCustomerById(customer);
        vehicle.setCustomer(selectedCustomer);
        CustomerVehicle savedVehicle = vehicleService.saveVehicle(vehicle);

        // Redirect to insurance plan selection with customerId and vehicleId
        redirectAttributes.addAttribute("customerId", selectedCustomer.getId());
        redirectAttributes.addAttribute("vehicleId", savedVehicle.getId());
        return "redirect:/insurance-plan/select-plan";
    }


    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/customer-vehicle/list";
    }
}
