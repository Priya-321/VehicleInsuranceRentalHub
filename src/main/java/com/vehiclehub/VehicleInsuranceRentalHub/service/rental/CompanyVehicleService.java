package com.vehiclehub.VehicleInsuranceRentalHub.service.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;

import java.util.List;

public interface CompanyVehicleService {

    CompanyVehicle saveVehicle(CompanyVehicle vehicle);
    List<CompanyVehicle> getAllVehicles();
    CompanyVehicle getVehicleById(int id);
    void deleteVehicle(int id);

    List<CompanyVehicle> getVehiclesByStatus(String status); // e.g., Available, Booked
}
