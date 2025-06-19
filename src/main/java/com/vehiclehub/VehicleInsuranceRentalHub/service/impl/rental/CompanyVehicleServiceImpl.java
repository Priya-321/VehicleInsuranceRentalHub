package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.rental;

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
        Optional<CompanyVehicle> optional = vehicleRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<CompanyVehicle> getVehiclesByStatus(String status) {
        return vehicleRepository.findByStatus(status);
    }
}
