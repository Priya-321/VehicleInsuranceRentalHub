package com.vehiclehub.VehicleInsuranceRentalHub.model.insurance;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class InsuranceCustomer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phone;
    private String address;

    @ManyToOne // Many customers served by one agent
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerVehicle> vehicles; //one customer can have many vehicles for policies

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<CustomerVehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<CustomerVehicle> vehicles) {
		this.vehicles = vehicles;
	}

	@Override
	public String toString() {
		return "InsuranceCustomer [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", agent=" + agent + ", vehicles=" + vehicles + "]";
	}
    
    
}
