package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.common;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AdminRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //identifies this is a service bean -> look for matching bean (Admin Repository)
public class AdminServiceImpl implements AdminService {

    @Autowired //used for dependency injection
    private AdminRepository adminRepository;
    //creates object of AdminRepository -> injects AdminRepository so this class can access the database
    
    @Override //overriding method defined in parent class
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(int id) {
        Optional<Admin> optional = adminRepository.findById(id); //optional class for handling null values
        return optional.orElse(null); // here we can also throw custom exception if preferred
    }

    @Override
    public void deleteAdmin(int id) {
        adminRepository.deleteById(id);
    }
}
