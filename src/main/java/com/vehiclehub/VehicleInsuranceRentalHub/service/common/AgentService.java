package com.vehiclehub.VehicleInsuranceRentalHub.service.common;

import java.util.List;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;

public interface AgentService {

	Agent saveAgent(Agent agent);
	List<Agent> getAllAgents();
	Agent getAgentById(int id); //fetch agent info based on id
	void deleteAgent(int id);
	List<Agent> getAgentsByRole(String role); //fetch agents info on the basis of role
}
