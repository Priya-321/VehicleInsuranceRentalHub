package com.vehiclehub.VehicleInsuranceRentalHub.controller.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.CompanyVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        if (query.matches("\\d+")) { // ID Search
            try {
                CompanyVehicle vehicle = vehicleService.getVehicleById(Integer.parseInt(query));
                vehicles = List.of(vehicle);
            } catch (NotFoundException e) {
                vehicles = vehicleService.getAllVehicles(); // fallback list
                model.addAttribute("errorMessage", e.getMessage());
            }
        } else { // model Search
            vehicles = vehicleService.searchByModel(query);

            if (vehicles.isEmpty()) {
                vehicles =vehicleService.getAllVehicles(); // fallback list
                model.addAttribute("errorMessage", "No vehicles found for model: " + query);
            }
        }

        model.addAttribute("vehicles", vehicles);
        return "companyVehicle/list";
    }



    
    @GetMapping("/form")
    public String showVehicleForm(Model model) {
        model.addAttribute("vehicle", new CompanyVehicle());
        return "company_vehicle/form";
    }

    @PostMapping("/save")
    public String saveVehicle(@ModelAttribute CompanyVehicle vehicle) {
        vehicleService.saveVehicle(vehicle);
        return "redirect:/company-vehicle/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/company-vehicle/list";
    }
}
