package com.vehiclehub.VehicleInsuranceRentalHub.service.customUserDetail;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AdminRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AgentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AgentRepository agentRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try admin first
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                admin.getEmail(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority("ADMIN"))
            );
        }

        // Try agent
        Optional<Agent> agentOpt = agentRepo.findByEmail(email);
        if (agentOpt.isPresent()) {
            Agent agent = agentOpt.get();
            return new org.springframework.security.core.userdetails.User(
                agent.getEmail(),
                agent.getPassword(),
                List.of(new SimpleGrantedAuthority(agent.getRole().toUpperCase()))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
