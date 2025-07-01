package com.vehiclehub.VehicleInsuranceRentalHub.service.common;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;

import java.util.List;

public interface AdminService {
//This is an interface that defines the business operations available for the Admin entity.
//use these methods in controllers to interact with the database through logic.    
	
	Admin saveAdmin(Admin admin);//saves an admin or update existing
    List<Admin> getAllAdmins();//fetch all records
    Admin getAdminById(int id);//get a specific admin by id
    void deleteAdmin(int id);// remove an admin by id
    List<Admin> searchByName(String name);
    
    void updatePassword(int id, String hashedPassword);
    Admin findByEmail(String email);

}
