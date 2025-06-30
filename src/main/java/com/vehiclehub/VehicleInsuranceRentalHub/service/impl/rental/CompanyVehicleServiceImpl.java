package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.CompanyVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.rental.CompanyVehicleRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.CompanyVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyVehicleServiceImpl implements CompanyVehicleService {

    @Autowired
    private CompanyVehicleRepository vehicleRepository;

    @Override
    public CompanyVehicle saveVehicle(CompanyVehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<CompanyVehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public CompanyVehicle getVehicleById(int id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Company vehicle with ID " + id + " not found."));
    }


    @Override
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<CompanyVehicle> getVehiclesByStatus(String status) {
        return vehicleRepository.findByStatus(status);
    }
    
    @Override
    public List<CompanyVehicle> searchByModel(String model) {
        return vehicleRepository.findByModelContainingIgnoreCase(model);
    }

}
