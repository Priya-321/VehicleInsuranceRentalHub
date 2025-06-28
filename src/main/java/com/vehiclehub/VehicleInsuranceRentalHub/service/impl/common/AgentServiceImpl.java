package com.vehiclehub.VehicleInsuranceRentalHub.service.impl.common;

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
        Optional<Agent> optional = agentRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void deleteAgent(int id) {
        agentRepository.deleteById(id);
    }

    @Override
    public List<Agent> getAgentsByRole(String role) {
        return agentRepository.findByRole(role); //here findByRole is a custom method so we need to define it in repository as well
    }
}
