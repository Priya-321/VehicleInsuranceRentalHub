package com.vehiclehub.VehicleInsuranceRentalHub.controller.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.CompanyVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/company-vehicle")
public class CompanyVehicleController {

    @Autowired
    private CompanyVehicleService vehicleService;

    @GetMapping("/list")
    public String listVehicles(@RequestParam(name = "status", required = false) String status, Model model) {
        List<CompanyVehicle> vehicles;

        if (status != null && !status.isEmpty()) {
            vehicles = vehicleService.getVehiclesByStatus(status);
            model.addAttribute("filter", status);
        } else {
            vehicles = vehicleService.getAllVehicles();
            model.addAttribute("filter", "");
        }

        model.addAttribute("vehicles", vehicles);
        return "company_vehicle/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<CompanyVehicle> vehicles;

        if (query.matches("\\d+")) {
            try {
                CompanyVehicle vehicle = vehicleService.getVehicleById(Integer.parseInt(query));
                vehicles = List.of(vehicle);
            } catch (NotFoundException e) {
                vehicles = vehicleService.getAllVehicles();
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else {
            vehicles = vehicleService.searchByModel(query);

            if (vehicles.isEmpty()) {
                vehicles = vehicleService.getAllVehicles();
                model.addAttribute("errorMessage", "No vehicles found for model: " + query);
            }
        }

        model.addAttribute("vehicles", vehicles);
        return "company_vehicle/list";
    }

    @GetMapping("/form")
    public String showVehicleForm(Model model) {
        model.addAttribute("vehicle", new CompanyVehicle());
        return "company_vehicle/form";
    }
    
    
    @PostMapping("/save")
    public String saveVehicle(@ModelAttribute CompanyVehicle vehicle, RedirectAttributes redirectAttributes) {
        vehicleService.saveVehicle(vehicle);
        redirectAttributes.addFlashAttribute("successMessage", "Vehicle added successfully!");
        return "redirect:/company-vehicle/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/company-vehicle/list";
    }

    //STEP 2: Select available vehicle after choosing or adding customer
    @GetMapping("/select")
    public String selectVehicle(@RequestParam("customerId") int customerId, Model model) {
        List<CompanyVehicle> availableVehicles = vehicleService.getVehiclesByStatus("Available");
        model.addAttribute("vehicles", availableVehicles);
        model.addAttribute("customerId", customerId);
        return "company_vehicle/select";
    }

    //STEP 3: Redirect to rental booking form with both customerId and vehicleId
    @GetMapping("/choose/{id}")
    public String chooseVehicle(@PathVariable int id, @RequestParam("customerId") int customerId) {
        return "redirect:/rental-booking/form?customerId=" + customerId + "&vehicleId=" + id;
    }
}
