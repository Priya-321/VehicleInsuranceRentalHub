package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.insurance.InsuranceCustomer;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.insurance.InsuranceCustomerRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.insurance.InsuranceCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceCustomerServiceImpl implements InsuranceCustomerService {

    @Autowired
    private InsuranceCustomerRepository customerRepository;

    @Override
    public InsuranceCustomer saveCustomer(InsuranceCustomer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<InsuranceCustomer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public InsuranceCustomer getCustomerById(int id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Insurance customer with ID " + id + " not found."));
    }


    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<InsuranceCustomer> getCustomersByAgentId(int agentId) {
        return customerRepository.findByAgentId(agentId);
    }

    @Override
    public List<InsuranceCustomer> searchByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }
}
