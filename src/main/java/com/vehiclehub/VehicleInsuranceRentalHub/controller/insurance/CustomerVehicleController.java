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
        return "customerVehicle/list";
    }



    @GetMapping("/form")
    public String showVehicleForm(Model model) {
        model.addAttribute("vehicle", new CustomerVehicle());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer_vehicle/form";
    }

    @PostMapping("/save")
    public String saveVehicle(@ModelAttribute CustomerVehicle vehicle, @RequestParam int customer) {
        InsuranceCustomer selectedCustomer = customerService.getCustomerById(customer);
        vehicle.setCustomer(selectedCustomer);
        vehicleService.saveVehicle(vehicle);
        return "redirect:/customer-vehicle/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/customer-vehicle/list";
    }
}
