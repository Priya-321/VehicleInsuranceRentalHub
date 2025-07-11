package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.CustomerVehicle;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance.CustomerVehicleRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.CustomerVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerVehicleServiceImpl implements CustomerVehicleService {

    @Autowired
    private CustomerVehicleRepository vehicleRepository;

    @Override
    public CustomerVehicle saveVehicle(CustomerVehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<CustomerVehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public CustomerVehicle getVehicleById(int id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Customer vehicle with ID " + id + " not found."));
    }


    @Override
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<CustomerVehicle> getVehiclesByCustomerId(int customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    @Override
    public CustomerVehicle getVehicleByRegistrationNumber(String regNo) {
        return vehicleRepository.findByRegistrationNumber(regNo);
    }
    
    @Override
    public List<CustomerVehicle> searchByName(String name) {
        return vehicleRepository.findByCustomerNameContainingIgnoreCase(name);
    }
}
