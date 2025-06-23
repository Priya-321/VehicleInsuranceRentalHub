package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.insurance;

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
        Optional<CustomerVehicle> optional = vehicleRepository.findById(id);
        return optional.orElse(null);
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
}
