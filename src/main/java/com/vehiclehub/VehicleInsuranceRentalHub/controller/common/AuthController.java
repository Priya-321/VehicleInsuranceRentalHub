package com.vehiclehub.VehicleInsuranceRentalHub.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Thymeleaf template name
    }

  
}
