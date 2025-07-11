package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.common;

import com.vehiclehub.VehicleInsuranceRentalHub.exception.NotFoundException;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import com.vehiclehub.VehicleInsuranceRentalHub.repository.common.AgentRepository;
import com.vehiclehub.VehicleInsuranceRentalHub.service.common.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public Agent saveAgent(Agent agent) {
    	    if (agent.getPassword() != null && !agent.getPassword().startsWith("$2a$")) {
            agent.setPassword(passwordEncoder.encode(agent.getPassword()));
            }
        return agentRepository.save(agent);
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentById(int id) {
    	        return agentRepository.findById(id)
    	        .orElseThrow(() -> new NotFoundException("Agent with ID " + id + " not found."));
    }

    
    @Override
    public void deleteAgent(int id) {
        agentRepository.deleteById(id);
    }

    @Override
    public List<Agent> getAgentsByRole(String role) {
        return agentRepository.findByRole(role); //here findByRole is a custom method so we need to define it in repository as well
    }
    
    @Override
    public List<Agent> searchByName(String name) {
        return agentRepository.findByNameContainingIgnoreCase(name);
    }
    
    
    @Override
    public void updatePhoneAndPassword(int id, String phone, String hashedPassword) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agent not found with ID: " + id));
        agent.setPhone(phone);
        agent.setPassword(hashedPassword);
        agentRepository.save(agent);
    }

    @Override
    public void updatePhoneOnly(int id, String phone) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Agent not found with ID: " + id));
        agent.setPhone(phone);
        agentRepository.save(agent);
    }


}
