package com.vehiclehub.VehicleInsuranceRentalHub.model.common;

import jakarta.persistence.*;

@Entity
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
    private String email;
    private String phone;
    private String role; // "RENTAL" or "INSURANCE"
    private String password;

    
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


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", role=" + role
				+ ", password=" + password + "]";
	}


	


	
	
}
