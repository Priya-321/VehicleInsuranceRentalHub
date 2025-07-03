package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.common;


import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AdminRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service //identifies this is a service bean -> look for matching bean (Admin Repository)
public class AdminServiceImpl implements AdminService {

    @Autowired //used for dependency injection
    private AdminRepository adminRepository;
    //creates object of AdminRepository -> injects AdminRepository so this class can access the database
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    
    @Override //overriding method defined in parent class
    public Admin saveAdmin(Admin admin) {
        // hash password only if it's plain text
        if (admin.getPassword() != null && !admin.getPassword().startsWith("$2a$")) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }
        return adminRepository.save(admin);
    }


    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(int id) {
        //Optional<Admin> optional = adminRepository.findById(id); //optional class for handling null values
        //return optional.orElse(null); // here we can also throw custom exception if preferred
        return adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin with ID " + id + " not found."));
    }


    @Override
    public void deleteAdmin(int id) {
        adminRepository.deleteById(id);
    }
    
    @Override
    public List<Admin> searchByName(String name) {
        return adminRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Transactional
    @Override
    public void updatePassword(int id, String hashedPassword) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin not found with ID: " + id));
        admin.setPassword(hashedPassword);
        adminRepository.save(admin);
    }

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Admin not found with email: " + email));
    }


}
