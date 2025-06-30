package com.vehiclehub.VehicleInsuranceRentalHub.controller.common;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AdminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/list")
	public String listAdmins(Model model) {
		List<Admin> admins = adminService.getAllAdmins();
		model.addAttribute("admins", admins);
		return "admin/list";
	}

	@GetMapping("/search")
	public String search(@RequestParam("query") String query, Model model) {
	    List<Admin> admins;

	    if (query.matches("\\d+")) { // ID Search
	        try {
	            Admin admin = adminService.getAdminById(Integer.parseInt(query));
	            admins = List.of(admin);
	        } catch (NotFoundException e) {
	            admins = adminService.getAllAdmins(); // fallback list
	            model.addAttribute("errorMessage", e.getMessage());
	        }
	    } else { // Name Search
	        admins = adminService.searchByName(query);

	        if (admins.isEmpty()) {
	            admins = adminService.getAllAdmins(); // fallback list
	            model.addAttribute("errorMessage", "No admins found for name: " + query);
	        }
	    }

	    model.addAttribute("admins", admins);
	    return "admin/list";
	}



	
	@GetMapping("/form")
    public String showAdminForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/form"; // loads templates/admin/form.html
    }

    @PostMapping("/save")
    public String saveAdmin(@ModelAttribute Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/list";
    }
	
}
