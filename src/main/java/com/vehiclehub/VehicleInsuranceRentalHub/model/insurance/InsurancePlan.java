package com.vehiclehub.VehicleInsuranceRentalHub.model.insurance;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class InsurancePlan {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;        // e.g., "Comprehensive", "Third-Party"
    private String description;
    private Double premium;     // Cost
    private int durationInMonths;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Policy> policies; //one plan can be selected in many policies

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public int getDurationInMonths() {
		return durationInMonths;
	}

	public void setDurationInMonths(int durationInMonths) {
		this.durationInMonths = durationInMonths;
	}

	public List<Policy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<Policy> policies) {
		this.policies = policies;
	}

	@Override
	public String toString() {
		return "InsurancePlan [id=" + id + ", name=" + name + ", description=" + description + ", premium=" + premium
				+ ", durationInMonths=" + durationInMonths + ", policies=" + policies + "]";
	}
    
    
}
