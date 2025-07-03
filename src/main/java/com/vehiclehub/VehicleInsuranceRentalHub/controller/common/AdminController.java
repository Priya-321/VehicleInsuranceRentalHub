package com.vehiclehub.VehicleInsuranceRentalHub.controller.common;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AdminService;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AgentService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	
	
	@GetMapping("/list")
	public String listAdmins(Model model, Principal principal) {
		List<Admin> admins = adminService.getAllAdmins();
		model.addAttribute("admins", admins);
		
		model.addAttribute("loggedInEmail", principal.getName());
		
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
    
    
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping("/edit-password")
    public String showEditAdminPasswordForm(Model model, Principal principal) {
        Admin admin = adminService.findByEmail(principal.getName());
        model.addAttribute("admin", admin);
        return "admin/edit-password"; // templates/admin/edit-password.html
    }

    @PostMapping("/update-password")
    public String updateAdminPassword(@RequestParam("id") int id,
                                      @RequestParam("newPassword") String newPassword,
                                      RedirectAttributes redirectAttributes) {

        System.out.println("Received password update request for ID: " + id);
        System.out.println("New password (raw): " + newPassword); // Only for testing!

        String hashedPassword = passwordEncoder.encode(newPassword);
        adminService.updatePassword(id, hashedPassword);

        System.out.println("Hashed password stored: " + hashedPassword);
        redirectAttributes.addFlashAttribute("successMessage", "Password updated successfully!");
        return "redirect:/admin/list";
    }


    @Autowired
    private AgentService agentService;
    
    @GetMapping("/edit-agent/{id}")
    public String showEditAgentForm(@PathVariable int id, Model model) {
        Agent agent = agentService.getAgentById(id);
        model.addAttribute("agent", agent);
        return "admin/edit-agent"; // templates/admin/edit-agent.html
    }

    @PostMapping("/update-agent/{id}")
    public String updateAgentDetails(@PathVariable int id,
                                     @RequestParam("phone") String phone,
                                     @RequestParam(value = "newPassword", required = false) String newPassword,
                                     RedirectAttributes redirectAttributes) {
        if (newPassword != null && !newPassword.isBlank()) {
            String hashedPassword = passwordEncoder.encode(newPassword);
            agentService.updatePhoneAndPassword(id, phone, hashedPassword);
        } else {
            agentService.updatePhoneOnly(id, phone); 
        }

        redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        return "redirect:/agent/list";
    }

    
	
}
