package com.vehiclehub.VehicleInsuranceRentalHub.service.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsuranceCustomer;

import java.util.List;

public interface InsuranceCustomerService {

    InsuranceCustomer saveCustomer(InsuranceCustomer customer);
    List<InsuranceCustomer> getAllCustomers();
    InsuranceCustomer getCustomerById(int id);
    void deleteCustomer(int id);

    List<InsuranceCustomer> getCustomersByAgentId(int agentId);
    List<InsuranceCustomer> searchByName(String name);
}
