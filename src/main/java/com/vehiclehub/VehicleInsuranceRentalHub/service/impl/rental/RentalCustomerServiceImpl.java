package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.rental;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.ResourceNotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.rental.RentalCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.rental.RentalCustomerRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.rental.RentalCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalCustomerServiceImpl implements RentalCustomerService {

    @Autowired
    private RentalCustomerRepository rentalCustomerRepository;

    @Override
    public RentalCustomer saveCustomer(RentalCustomer customer) {
        return rentalCustomerRepository.save(customer);
    }

    @Override
    public List<RentalCustomer> getAllCustomers() {
        return rentalCustomerRepository.findAll();
    }

    @Override
    public RentalCustomer getCustomerById(int id) {
        //Optional<RentalCustomer> optional = rentalCustomerRepository.findById(id);
        //return optional.orElse(null);
      	return rentalCustomerRepository.findById(id)
    	        .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public void deleteCustomer(int id) {
        rentalCustomerRepository.deleteById(id);
    }

    @Override
    public List<RentalCustomer> getCustomersByAgentId(int agentId) {
        return rentalCustomerRepository.findByAgentId(agentId);
    }
}
