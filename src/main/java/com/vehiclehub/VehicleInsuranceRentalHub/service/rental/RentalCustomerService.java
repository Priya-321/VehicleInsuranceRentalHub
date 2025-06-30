package com.vehiclehub.VehicleInsuranceRentalHub.service.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;

import java.util.List;

public interface RentalCustomerService {

    RentalCustomer saveCustomer(RentalCustomer customer);
    List<RentalCustomer> getAllCustomers();
    RentalCustomer getCustomerById(int id);
    void deleteCustomer(int id);

    //custom method
    List<RentalCustomer> getCustomersByAgentId(int agentId);
    List<RentalCustomer> searchByName(String name);
}
