package com.vehiclehub.VehicleInsuranceRentalHub.controller.insurance;

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
    public String listVehicles(@RequestParam(name = "customerId", required = false) Integer customerId, Model model) {
        List<CustomerVehicle> vehicles;

        if (customerId != null) {
            vehicles = vehicleService.getVehiclesByCustomerId(customerId);
            model.addAttribute("selectedCustomerId", customerId);
        } else {
            vehicles = vehicleService.getAllVehicles();
            model.addAttribute("selectedCustomerId", "");
        }

        List<InsuranceCustomer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("vehicles", vehicles);
        return "customer_vehicle/list";
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
