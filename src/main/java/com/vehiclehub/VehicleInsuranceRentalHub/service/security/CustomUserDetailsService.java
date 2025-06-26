package com.vehiclehub.VehicleInsuranceRentalHub.service.security;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.model.security.CustomUserDetails;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AdminRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(username);
        if (admin != null) {
            return new CustomUserDetails(admin);
        }

        Agent agent = agentRepository.findByEmail(username);
        if (agent != null) {
            return new CustomUserDetails(agent);
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
