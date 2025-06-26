package com.vehiclehub.VehicleInsuranceRentalHub.controller.common;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
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
	
	@GetMapping("/dashboard")
	public String adminDashboard() {
	    return "admin/dashboard";
	}
	
	@GetMapping("/list")
	public String listAdmins(Model model) {
		List<Admin> admins = adminService.getAllAdmins();
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
