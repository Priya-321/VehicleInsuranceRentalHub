package com.vehiclehub.VehicleInsuranceRentalHub.service.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.CustomerVehicle;

import java.util.List;

public interface CustomerVehicleService {

    CustomerVehicle saveVehicle(CustomerVehicle vehicle);
    List<CustomerVehicle> getAllVehicles();
    CustomerVehicle getVehicleById(int id);
    void deleteVehicle(int id);

    List<CustomerVehicle> getVehiclesByCustomerId(int customerId);
    CustomerVehicle getVehicleByRegistrationNumber(String regNo);
    List<CustomerVehicle> searchByName(String name);
}
