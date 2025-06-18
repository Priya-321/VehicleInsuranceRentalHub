package com.vehiclehub.VehicleInsuranceRentalHub.model.insurance;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Policy {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Active, Expired, Cancelled, etc.

    @ManyToOne //many policies can be related to one vehicle
    @JoinColumn(name = "vehicle_id")
    private CustomerVehicle vehicle;

    @ManyToOne //many policies can have one plan type
    @JoinColumn(name = "plan_id")
    private InsurancePlan plan;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private List<Claim> claims; //one policy can have multiple claims

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustomerVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(CustomerVehicle vehicle) {
		this.vehicle = vehicle;
	}

	public InsurancePlan getPlan() {
		return plan;
	}

	public void setPlan(InsurancePlan plan) {
		this.plan = plan;
	}

	public List<Claim> getClaims() {
		return claims;
	}

	public void setClaims(List<Claim> claims) {
		this.claims = claims;
	}

	@Override
	public String toString() {
		return "Policy [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status
				+ ", vehicle=" + vehicle + ", plan=" + plan + ", claims=" + claims + "]";
	}

    
}
